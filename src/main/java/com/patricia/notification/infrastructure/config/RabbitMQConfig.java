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

    // Exchanges
    @Value("${rabbitmq.exchange.auth}")
    private String authExchange;

    @Value("${rabbitmq.exchange.parche}")
    private String parcheExchange;

    @Value("${rabbitmq.exchange.social}")
    private String socialExchange;

    @Value("${rabbitmq.exchange.hangout}")
    private String hangoutExchange;

    @Value("${rabbitmq.exchange.dlx}")
    private String dlxExchange;

    // Queues
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

    @Value("${rabbitmq.queue.invitation-accepted}")
    private String invitationAcceptedQueue;

    @Value("${rabbitmq.queue.invitation-sent}")
    private String invitationSentQueue;

    @Value("${rabbitmq.queue.member-joined}")
    private String memberJoinedQueue;

    // Routing Keys
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

    @Value("${rabbitmq.routing-key.invitation-accepted}")
    private String invitationAcceptedRoutingKey;

    @Value("${rabbitmq.routing-key.invitation-sent}")
    private String invitationSentRoutingKey;

    @Value("${rabbitmq.routing-key.member-joined}")
    private String memberJoinedRoutingKey;

    // ─── Exchanges ────────────────────────────────────────────────────────────

    @Bean public TopicExchange authExchange()    { return new TopicExchange(authExchange); }
    @Bean public TopicExchange parcheExchange()  { return new TopicExchange(parcheExchange); }
    @Bean public TopicExchange socialExchange()  { return new TopicExchange(socialExchange); }
    @Bean public TopicExchange hangoutExchange() { return new TopicExchange(hangoutExchange); }

    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(dlxExchange);
    }

    // ─── Main queues (with DLX args) ──────────────────────────────────────────

    @Bean public Queue otpVerificationQueue()  { return withDlx(otpVerificationQueue); }
    @Bean public Queue otpResendQueue()        { return withDlx(otpResendQueue); }
    @Bean public Queue passwordResetQueue()    { return withDlx(passwordResetQueue); }
    @Bean public Queue parcheInvitationQueue() { return withDlx(parcheInvitationQueue); }
    @Bean public Queue nearbyParcheQueue()     { return withDlx(nearbyParcheQueue); }
    @Bean public Queue connectionRequestQueue(){ return withDlx(connectionRequestQueue); }
    @Bean public Queue invitationAcceptedQueue(){ return withDlx(invitationAcceptedQueue); }
    @Bean public Queue invitationSentQueue()   { return withDlx(invitationSentQueue); }
    @Bean public Queue memberJoinedQueue()     { return withDlx(memberJoinedQueue); }

    private Queue withDlx(String queueName) {
        return QueueBuilder.durable(queueName)
                .withArgument("x-dead-letter-exchange", dlxExchange)
                .withArgument("x-dead-letter-routing-key", queueName + ".dlq")
                .build();
    }

    // ─── Dead Letter Queues ───────────────────────────────────────────────────

    @Bean public Queue otpVerificationDlq()  { return new Queue(otpVerificationQueue + ".dlq"); }
    @Bean public Queue otpResendDlq()        { return new Queue(otpResendQueue + ".dlq"); }
    @Bean public Queue passwordResetDlq()    { return new Queue(passwordResetQueue + ".dlq"); }
    @Bean public Queue parcheInvitationDlq() { return new Queue(parcheInvitationQueue + ".dlq"); }
    @Bean public Queue nearbyParcheDlq()     { return new Queue(nearbyParcheQueue + ".dlq"); }
    @Bean public Queue connectionRequestDlq(){ return new Queue(connectionRequestQueue + ".dlq"); }
    @Bean public Queue invitationAcceptedDlq(){ return new Queue(invitationAcceptedQueue + ".dlq"); }
    @Bean public Queue invitationSentDlq()   { return new Queue(invitationSentQueue + ".dlq"); }
    @Bean public Queue memberJoinedDlq()     { return new Queue(memberJoinedQueue + ".dlq"); }

    // ─── DLQ Bindings (DLQ → DLX) ────────────────────────────────────────────

    @Bean public Binding otpVerificationDlqBinding()  { return dlqBinding(otpVerificationDlq(), otpVerificationQueue); }
    @Bean public Binding otpResendDlqBinding()        { return dlqBinding(otpResendDlq(), otpResendQueue); }
    @Bean public Binding passwordResetDlqBinding()    { return dlqBinding(passwordResetDlq(), passwordResetQueue); }
    @Bean public Binding parcheInvitationDlqBinding() { return dlqBinding(parcheInvitationDlq(), parcheInvitationQueue); }
    @Bean public Binding nearbyParcheDlqBinding()     { return dlqBinding(nearbyParcheDlq(), nearbyParcheQueue); }
    @Bean public Binding connectionRequestDlqBinding(){ return dlqBinding(connectionRequestDlq(), connectionRequestQueue); }
    @Bean public Binding invitationAcceptedDlqBinding(){ return dlqBinding(invitationAcceptedDlq(), invitationAcceptedQueue); }
    @Bean public Binding invitationSentDlqBinding()   { return dlqBinding(invitationSentDlq(), invitationSentQueue); }
    @Bean public Binding memberJoinedDlqBinding()     { return dlqBinding(memberJoinedDlq(), memberJoinedQueue); }

    private Binding dlqBinding(Queue dlq, String originalQueueName) {
        return BindingBuilder.bind(dlq)
                .to(dlxExchange())
                .with(originalQueueName + ".dlq");
    }

    // ─── Main queue Bindings ──────────────────────────────────────────────────

    @Bean
    public Binding otpVerificationBinding() {
        return BindingBuilder.bind(otpVerificationQueue()).to(authExchange()).with(otpVerificationRoutingKey);
    }

    @Bean
    public Binding otpResendBinding() {
        return BindingBuilder.bind(otpResendQueue()).to(authExchange()).with(otpResendRoutingKey);
    }

    @Bean
    public Binding passwordResetBinding() {
        return BindingBuilder.bind(passwordResetQueue()).to(authExchange()).with(passwordResetRoutingKey);
    }

    @Bean
    public Binding parcheInvitationBinding() {
        return BindingBuilder.bind(parcheInvitationQueue()).to(parcheExchange()).with(parcheInvitationRoutingKey);
    }

    @Bean
    public Binding nearbyParcheBinding() {
        return BindingBuilder.bind(nearbyParcheQueue()).to(parcheExchange()).with(nearbyParcheRoutingKey);
    }

    @Bean
    public Binding connectionRequestBinding() {
        return BindingBuilder.bind(connectionRequestQueue()).to(socialExchange()).with(connectionRequestRoutingKey);
    }

    @Bean
    public Binding invitationAcceptedBinding() {
        return BindingBuilder.bind(invitationAcceptedQueue()).to(hangoutExchange()).with(invitationAcceptedRoutingKey);
    }

    @Bean
    public Binding invitationSentBinding() {
        return BindingBuilder.bind(invitationSentQueue()).to(hangoutExchange()).with(invitationSentRoutingKey);
    }

    @Bean
    public Binding memberJoinedBinding() {
        return BindingBuilder.bind(memberJoinedQueue()).to(hangoutExchange()).with(memberJoinedRoutingKey);
    }

    // ─── Infrastructure ───────────────────────────────────────────────────────

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
