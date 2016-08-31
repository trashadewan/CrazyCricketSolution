package kafka;

/**
 * Created by trasha on 26/6/16.
 */

import com.google.common.io.Resources;
import dao.GameDAO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import pojo.CrazyCricketProtos;
import pojo.Game;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Consumer {

    private GameDAO gameDAO = new GameDAO();
    public void consume() throws IOException {
        // set up house-keeping

        // and the consumer
        KafkaConsumer<byte[], byte[]> consumer;
        InputStream props = Resources.getResource("Consumer.props").openStream();
        {
            Properties properties = new Properties();
            properties.load(props);
            if (properties.getProperty("group.id") == null) {
                properties.setProperty("group.id", "group-" + new Random().nextInt(100000));
            }
            consumer = new KafkaConsumer<>(properties);
        }

        TopicPartition topicPartition = new TopicPartition("TEST", 0);
        TopicPartition topicPartition1 = new TopicPartition("TWENTY_TWENTY", 0);
        TopicPartition topicPartition2 = new TopicPartition("LIMITED_OVERS", 0);
        consumer.assign(Arrays.asList(topicPartition,topicPartition1,topicPartition2));
        consumer.seekToBeginning(Arrays.asList(topicPartition,topicPartition1,topicPartition2));
//        consumer.assign(Arrays.asList(topicPartition1,topicPartition2));
//        consumer.seekToBeginning(topicPartition1,topicPartition2);
        //String topicName = "TEST";
        //consumer.subscribe(Arrays.asList(topicName ,"TWENTY_TWENTY"));

        int timeouts = 0;
        //noinspection InfiniteLoopStatement
        while (true) {
            // read records with a short timeout. If we time out, we don't really care.
            ConsumerRecords<byte[], byte[]> records = consumer.poll(200);
            if (records.count() == 0) {
                timeouts++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            } else {
                System.out.printf("Got %d records after %d timeouts\n", records.count(), timeouts);
                timeouts = 0;
            }
            List<Game> gameList= new ArrayList<Game>();
            for (ConsumerRecord<byte[], byte[]> record : records) {
                //System.out.println(record.toString());
                CrazyCricketProtos.Game game = CrazyCricketProtos.Game.parseFrom(record.value());
                Game game1 = new Game(game);
                gameList.add(game1);
                System.out.println(game.toString());
            }
            gameDAO.saveGameList(gameList);
        }
    }

    public static void main(String[] args){
        System.out.println("In Main");
        Consumer consumer = new Consumer();
        try {
            consumer.consume();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
