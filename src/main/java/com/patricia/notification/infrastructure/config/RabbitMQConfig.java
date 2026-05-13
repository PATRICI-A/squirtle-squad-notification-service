package com.patricia.notification.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    //Exchanges
    @Value("${rabbitmq.exchange.auth}")
    private String authExchange;

    @Value("${rabbitmq.exchange.parche}")
    private String parcheExchange;

    @Value("${rabbitmq.exchange.social}")
    private String socialExchange;

    @Value("${rabbitmq.exchange.hangout}")
    private String hangoutExchange;

    //Queues
    @Value("${rabbitmq.queue.otp-verification}")
    private String otpVerificationQueue;

    @Value("${rabbitmq.queue.otp-resend}")
    private String otpResendQueue;

    @Value("${rabbitmq.queue.password-reset}")
    private String passwordResetQueue;

    @Value("${rabbitmq.queue.parche-invitation}")
    private String parcheInvitationQueue;

    @Value("${rabbitmq.queue.nearby-parche}")
    private String nearbyParcheQueue;

    @Value("${rabbitmq.queue.connection-request}")
    private String connectionRequestQueue;

    //Routing Keys
    @Value("${rabbitmq.routing-key.otp-verification}")
    private String otpVerificationRoutingKey;

    @Value("${rabbitmq.routing-key.otp-resend}")
    private String otpResendRoutingKey;

    @Value("${rabbitmq.routing-key.password-reset}")
    private String passwordResetRoutingKey;

    @Value("${rabbitmq.routing-key.parche-invitation}")
    private String parcheInvitationRoutingKey;

    @Value("${rabbitmq.routing-key.nearby-parche}")
    private String nearbyParcheRoutingKey;

    @Value("${rabbitmq.routing-key.connection-request}")
    private String connectionRequestRoutingKey;

    //Exchanges beans
    @Bean
    public TopicExchange authExchange() {
        return new TopicExchange(authExchange);
    }

    @Bean
    public TopicExchange parcheExchange() {
        return new TopicExchange(parcheExchange);
    }

    @Bean
    public TopicExchange socialExchange() {
        return new TopicExchange(socialExchange);
    }

    @Bean
    public TopicExchange hangoutExchange() {
        return new TopicExchange(hangoutExchange);
    }

    @Value("${rabbitmq.queue.invitation-accepted}")
    private String invitationAcceptedQueue;

    @Value("${rabbitmq.queue.invitation-sent}")
    private String invitationSentQueue;

    @Value("${rabbitmq.queue.member-joined}")
    private String memberJoinedQueue;

    @Value("${rabbitmq.routing-key.invitation-accepted}")
    private String invitationAcceptedRoutingKey;

    @Value("${rabbitmq.routing-key.invitation-sent}")
    private String invitationSentRoutingKey;

    @Value("${rabbitmq.routing-key.member-joined}")
    private String memberJoinedRoutingKey;

    //Queues beans
    @Bean public Queue otpVerificationQueue() { return new Queue(otpVerificationQueue); }
    @Bean public Queue otpResendQueue()        { return new Queue(otpResendQueue); }
    @Bean public Queue passwordResetQueue()    { return new Queue(passwordResetQueue); }
    @Bean public Queue parcheInvitationQueue() { return new Queue(parcheInvitationQueue); }
    @Bean public Queue nearbyParcheQueue()     { return new Queue(nearbyParcheQueue); }
    @Bean public Queue connectionRequestQueue(){ return new Queue(connectionRequestQueue); }
    @Bean public Queue invitationAcceptedQueue(){ return new Queue(invitationAcceptedQueue); }
    @Bean public Queue invitationSentQueue()    { return new Queue(invitationSentQueue); }
    @Bean public Queue memberJoinedQueue()      { return new Queue(memberJoinedQueue); }

    //Bindings
    @Bean
    public Binding otpVerificationBinding() {
        return BindingBuilder.bind(otpVerificationQueue())
                .to(authExchange())
                .with(otpVerificationRoutingKey);
    }

    @Bean
    public Binding otpResendBinding() {
        return BindingBuilder.bind(otpResendQueue())
                .to(authExchange())
                .with(otpResendRoutingKey);
    }

    @Bean
    public Binding passwordResetBinding() {
        return BindingBuilder.bind(passwordResetQueue())
                .to(authExchange())
                .with(passwordResetRoutingKey);
    }

    @Bean
    public Binding parcheInvitationBinding() {
        return BindingBuilder.bind(parcheInvitationQueue())
                .to(parcheExchange())
                .with(parcheInvitationRoutingKey);
    }

    @Bean
    public Binding nearbyParcheBinding() {
        return BindingBuilder.bind(nearbyParcheQueue())
                .to(parcheExchange())
                .with(nearbyParcheRoutingKey);
    }

    @Bean
    public Binding connectionRequestBinding() {
        return BindingBuilder.bind(connectionRequestQueue())
                .to(socialExchange())
                .with(connectionRequestRoutingKey);
    }

    @Bean
    public Binding invitationAcceptedBinding() {
        return BindingBuilder.bind(invitationAcceptedQueue())
                .to(hangoutExchange())
                .with(invitationAcceptedRoutingKey);
    }

    @Bean
    public Binding invitationSentBinding() {
        return BindingBuilder.bind(invitationSentQueue())
                .to(hangoutExchange())
                .with(invitationSentRoutingKey);
    }

    @Bean
    public Binding memberJoinedBinding() {
        return BindingBuilder.bind(memberJoinedQueue())
                .to(hangoutExchange())
                .with(memberJoinedRoutingKey);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    @Bean
    public ApplicationRunner declareRabbitTopology(RabbitAdmin rabbitAdmin) {
        return args -> rabbitAdmin.initialize();
    }

    //Convertidor JSON
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}