## RabbitMQ POC
This is a quick POC that tries to send two different objects:
- AlertMatch
- EmailVerification

to the same queue, "rabbitmq.json-mapped.queue". The objects are serialized to JSON in the publisher (ScheduledTasks class).
Both, publisher and consumer are configured wit a JSON converter which uses a class mapper.

### Failed Version

The RabbitMqConsumer::listenForJsonMappedMessages method should deserialize the JSON data to those 2 objects based on 
the __TypeId__ header automatically but it's not working and still receive the Message object. This method is annotated 
with @RabbitListener to the queue and using the containerFactory = "rabbitMqListenerContainerFactory".

### Working Version

There is an alternative version that works by using SimpleMessageListenerContainer and MessageListenerAdapter in the
RabbiMqConsumer class. This code is commented so far, for using it you need to comment and uncomment some code:
- comment RabbitMqConfiguration::rabbitMqListenerContainerFactory
- comment all the code in RabbitMqConsumer::listenForJsonMappedMessages and the annotation
- uncomment RabbitMqConfiguration::listenerContainer
- uncomment RabbitMqConfiguration::messageListener

## Run the project
On the root folder run
- docker compose up
- ./mvnw spring-boot:run ( or run it from your IDE)