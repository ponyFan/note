package com.test.design.builder;

import lombok.Data;

@Data
public class KafkaConfig {
    private String server;
    private String group;
    private String offset;
    private String partition;
    private String topic;

    KafkaConfig(KafkaConfigBuilder builder){
        this.server = builder.server;
        this.group = builder.group;
        this.offset = builder.offset;
        this.partition = builder.partition;
        this.topic = builder.topic;
    }

    public static class KafkaConfigBuilder{
        private String server;
        private String group;
        private String offset;
        private String partition;
        private String topic;

        public KafkaConfigBuilder(){

        }

        public KafkaConfigBuilder server(String server){
            this.server = server;
            return this;
        }

        public KafkaConfigBuilder group(String group){
            this.group = group;
            return this;
        }

        public KafkaConfigBuilder offset(String offset){
            this.offset = offset;
            return this;
        }

        public KafkaConfigBuilder partition(String partition){
            this.partition = partition;
            return this;
        }

        public KafkaConfigBuilder topic(String topic){
            this.topic = topic;
            return this;
        }

        public KafkaConfig build(){
            return new KafkaConfig(this);
        }
    }
}

