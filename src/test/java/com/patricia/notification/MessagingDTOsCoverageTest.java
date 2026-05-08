package com.patricia.notification;

import com.patricia.notification.entrypoints.messaging.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessagingDTOsCoverageTest {

    // =========================================================================
    // OtpVerificationEventDto
    // =========================================================================
    @Nested
    @DisplayName("OtpVerificationEventDto")
    class OtpVerificationEventDtoTests {

        @Test
        void noArgsConstructorAndSetters() {
            OtpVerificationEventDto dto = new OtpVerificationEventDto();
            dto.setEmail("user@example.com");
            dto.setOtpCode("123456");

            assertThat(dto.getEmail()).isEqualTo("user@example.com");
            assertThat(dto.getOtpCode()).isEqualTo("123456");
        }

        @Test
        void allArgsConstructor() {
            OtpVerificationEventDto dto = new OtpVerificationEventDto("a@b.com", "999");
            assertThat(dto.getEmail()).isEqualTo("a@b.com");
            assertThat(dto.getOtpCode()).isEqualTo("999");
        }

        @Test
        void equals_sameReference() {
            OtpVerificationEventDto dto = new OtpVerificationEventDto("a@b.com", "111");
            assertThat(dto.equals(dto)).isTrue();
        }

        @Test
        void equals_equalObjects() {
            OtpVerificationEventDto d1 = new OtpVerificationEventDto("a@b.com", "111");
            OtpVerificationEventDto d2 = new OtpVerificationEventDto("a@b.com", "111");
            assertThat(d1).isEqualTo(d2);
        }

        @Test
        void equals_nullAndDifferentType() {
            OtpVerificationEventDto dto = new OtpVerificationEventDto("a@b.com", "111");
            assertThat(dto.equals(null)).isFalse();
            assertThat(dto.equals("other")).isFalse();
        }

        @Test
        void equals_differentFieldValues() {
            OtpVerificationEventDto base = new OtpVerificationEventDto("a@b.com", "111");
            assertThat(base).isNotEqualTo(new OtpVerificationEventDto("x@b.com", "111"));
            assertThat(base).isNotEqualTo(new OtpVerificationEventDto("a@b.com", "999"));
        }

        @Test
        void equals_withNullFields() {
            OtpVerificationEventDto n1 = new OtpVerificationEventDto(null, "111");
            OtpVerificationEventDto n2 = new OtpVerificationEventDto(null, "111");
            OtpVerificationEventDto nn = new OtpVerificationEventDto("a@b.com", "111");

            assertThat(n1).isEqualTo(n2);
            assertThat(n1).isNotEqualTo(nn);
            assertThat(nn).isNotEqualTo(n1);
            assertThat(new OtpVerificationEventDto("a@b.com", null))
                    .isNotEqualTo(new OtpVerificationEventDto("a@b.com", "111"));
        }

        @Test
        void hashCode_consistent() {
            OtpVerificationEventDto d1 = new OtpVerificationEventDto("a@b.com", "111");
            OtpVerificationEventDto d2 = new OtpVerificationEventDto("a@b.com", "111");
            assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
            assertThat(new OtpVerificationEventDto(null, null).hashCode()).isNotZero();
        }

        @Test
        void toString_containsFields() {
            OtpVerificationEventDto dto = new OtpVerificationEventDto("a@b.com", "123456");
            assertThat(dto.toString()).contains("a@b.com").contains("123456");
        }

        @Test
        void builder_allFieldsAndToString() {
            String builderStr = OtpVerificationEventDto.builder()
                    .email("a@b.com")
                    .otpCode("123456")
                    .toString();
            assertThat(builderStr).isNotNull();
        }
    }

    // =========================================================================
    // OtpResendEventDto
    // =========================================================================
    @Nested
    @DisplayName("OtpResendEventDto")
    class OtpResendEventDtoTests {

        @Test
        void noArgsConstructorAndSetters() {
            OtpResendEventDto dto = new OtpResendEventDto();
            dto.setEmail("resend@example.com");
            dto.setOtpCode("654321");

            assertThat(dto.getEmail()).isEqualTo("resend@example.com");
            assertThat(dto.getOtpCode()).isEqualTo("654321");
        }

        @Test
        void allArgsConstructor() {
            OtpResendEventDto dto = new OtpResendEventDto("r@b.com", "777");
            assertThat(dto.getEmail()).isEqualTo("r@b.com");
            assertThat(dto.getOtpCode()).isEqualTo("777");
        }

        @Test
        void equals_sameReference() {
            OtpResendEventDto dto = new OtpResendEventDto("r@b.com", "777");
            assertThat(dto.equals(dto)).isTrue();
        }

        @Test
        void equals_equalObjects() {
            OtpResendEventDto d1 = new OtpResendEventDto("r@b.com", "777");
            OtpResendEventDto d2 = new OtpResendEventDto("r@b.com", "777");
            assertThat(d1).isEqualTo(d2);
        }

        @Test
        void equals_nullAndDifferentType() {
            OtpResendEventDto dto = new OtpResendEventDto("r@b.com", "777");
            assertThat(dto.equals(null)).isFalse();
            assertThat(dto.equals("other")).isFalse();
        }

        @Test
        void equals_differentFieldValues() {
            OtpResendEventDto base = new OtpResendEventDto("r@b.com", "777");
            assertThat(base).isNotEqualTo(new OtpResendEventDto("x@b.com", "777"));
            assertThat(base).isNotEqualTo(new OtpResendEventDto("r@b.com", "000"));
        }

        @Test
        void equals_withNullFields() {
            OtpResendEventDto n1 = new OtpResendEventDto(null, "777");
            OtpResendEventDto n2 = new OtpResendEventDto(null, "777");
            OtpResendEventDto nn = new OtpResendEventDto("r@b.com", "777");

            assertThat(n1).isEqualTo(n2);
            assertThat(n1).isNotEqualTo(nn);
            assertThat(nn).isNotEqualTo(n1);
            assertThat(new OtpResendEventDto("r@b.com", null))
                    .isNotEqualTo(new OtpResendEventDto("r@b.com", "777"));
        }

        @Test
        void hashCode_and_toString() {
            OtpResendEventDto d1 = new OtpResendEventDto("r@b.com", "777");
            OtpResendEventDto d2 = new OtpResendEventDto("r@b.com", "777");
            assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
            assertThat(new OtpResendEventDto(null, null).hashCode()).isNotZero();
            assertThat(d1.toString()).contains("r@b.com").contains("777");
        }

        @Test
        void builder_allFieldsAndToString() {
            String builderStr = OtpResendEventDto.builder()
                    .email("r@b.com")
                    .otpCode("777")
                    .toString();
            assertThat(builderStr).isNotNull();
        }
    }

    // =========================================================================
    // PasswordResetEventDto
    // =========================================================================
    @Nested
    @DisplayName("PasswordResetEventDto")
    class PasswordResetEventDtoTests {

        @Test
        void noArgsConstructorAndSetters() {
            PasswordResetEventDto dto = new PasswordResetEventDto();
            dto.setEmail("reset@example.com");
            dto.setResetCode("RESET-001");
            dto.setUserId("user-123");

            assertThat(dto.getEmail()).isEqualTo("reset@example.com");
            assertThat(dto.getResetCode()).isEqualTo("RESET-001");
            assertThat(dto.getUserId()).isEqualTo("user-123");
        }

        @Test
        void allArgsConstructor() {
            PasswordResetEventDto dto = new PasswordResetEventDto("p@b.com", "CODE", "uid");
            assertThat(dto.getEmail()).isEqualTo("p@b.com");
            assertThat(dto.getResetCode()).isEqualTo("CODE");
            assertThat(dto.getUserId()).isEqualTo("uid");
        }

        @Test
        void equals_sameReference() {
            PasswordResetEventDto dto = new PasswordResetEventDto("p@b.com", "CODE", "uid");
            assertThat(dto.equals(dto)).isTrue();
        }

        @Test
        void equals_equalObjects() {
            PasswordResetEventDto d1 = new PasswordResetEventDto("p@b.com", "CODE", "uid");
            PasswordResetEventDto d2 = new PasswordResetEventDto("p@b.com", "CODE", "uid");
            assertThat(d1).isEqualTo(d2);
        }

        @Test
        void equals_nullAndDifferentType() {
            PasswordResetEventDto dto = new PasswordResetEventDto("p@b.com", "CODE", "uid");
            assertThat(dto.equals(null)).isFalse();
            assertThat(dto.equals("other")).isFalse();
        }

        @Test
        void equals_differentFieldValues() {
            PasswordResetEventDto base = new PasswordResetEventDto("p@b.com", "CODE", "uid");
            assertThat(base).isNotEqualTo(new PasswordResetEventDto("x@b.com", "CODE", "uid"));
            assertThat(base).isNotEqualTo(new PasswordResetEventDto("p@b.com", "DIFF", "uid"));
            assertThat(base).isNotEqualTo(new PasswordResetEventDto("p@b.com", "CODE", "other"));
        }

        @Test
        void equals_withNullFields() {
            PasswordResetEventDto n1 = new PasswordResetEventDto(null, "CODE", "uid");
            PasswordResetEventDto n2 = new PasswordResetEventDto(null, "CODE", "uid");
            assertThat(n1).isEqualTo(n2);
            assertThat(n1).isNotEqualTo(new PasswordResetEventDto("p@b.com", "CODE", "uid"));
            assertThat(new PasswordResetEventDto("p@b.com", null, "uid"))
                    .isNotEqualTo(new PasswordResetEventDto("p@b.com", "CODE", "uid"));
        }

        @Test
        void hashCode_and_toString() {
            PasswordResetEventDto d1 = new PasswordResetEventDto("p@b.com", "CODE", "uid");
            PasswordResetEventDto d2 = new PasswordResetEventDto("p@b.com", "CODE", "uid");
            assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
            assertThat(new PasswordResetEventDto(null, null, null).hashCode()).isNotZero();
            assertThat(d1.toString()).contains("p@b.com").contains("CODE").contains("uid");
        }

        @Test
        void builder_allFieldsAndToString() {
            String builderStr = PasswordResetEventDto.builder()
                    .email("p@b.com")
                    .resetCode("CODE")
                    .userId("uid")
                    .toString();
            assertThat(builderStr).isNotNull();
        }
    }

    // =========================================================================
    // ConnectionRequestEventDto
    // =========================================================================
    @Nested
    @DisplayName("ConnectionRequestEventDto")
    class ConnectionRequestEventDtoTests {

        @Test
        void noArgsConstructorAndSetters() {
            ConnectionRequestEventDto dto = new ConnectionRequestEventDto();
            dto.setTargetUserId("target-1");
            dto.setRequesterUserName("Maria");
            dto.setRequesterId("req-1");

            assertThat(dto.getTargetUserId()).isEqualTo("target-1");
            assertThat(dto.getRequesterUserName()).isEqualTo("Maria");
            assertThat(dto.getRequesterId()).isEqualTo("req-1");
        }

        @Test
        void allArgsConstructor() {
            ConnectionRequestEventDto dto = new ConnectionRequestEventDto("t1", "Maria", "r1");
            assertThat(dto.getTargetUserId()).isEqualTo("t1");
            assertThat(dto.getRequesterUserName()).isEqualTo("Maria");
            assertThat(dto.getRequesterId()).isEqualTo("r1");
        }

        @Test
        void equals_sameReference() {
            ConnectionRequestEventDto dto = new ConnectionRequestEventDto("t1", "Maria", "r1");
            assertThat(dto.equals(dto)).isTrue();
        }

        @Test
        void equals_equalObjects() {
            ConnectionRequestEventDto d1 = new ConnectionRequestEventDto("t1", "Maria", "r1");
            ConnectionRequestEventDto d2 = new ConnectionRequestEventDto("t1", "Maria", "r1");
            assertThat(d1).isEqualTo(d2);
        }

        @Test
        void equals_nullAndDifferentType() {
            ConnectionRequestEventDto dto = new ConnectionRequestEventDto("t1", "Maria", "r1");
            assertThat(dto.equals(null)).isFalse();
            assertThat(dto.equals("other")).isFalse();
        }

        @Test
        void equals_differentFieldValues() {
            ConnectionRequestEventDto base = new ConnectionRequestEventDto("t1", "Maria", "r1");
            assertThat(base).isNotEqualTo(new ConnectionRequestEventDto("t2", "Maria", "r1"));
            assertThat(base).isNotEqualTo(new ConnectionRequestEventDto("t1", "Juan", "r1"));
            assertThat(base).isNotEqualTo(new ConnectionRequestEventDto("t1", "Maria", "r2"));
        }

        @Test
        void equals_withNullFields() {
            ConnectionRequestEventDto n1 = new ConnectionRequestEventDto(null, "Maria", "r1");
            ConnectionRequestEventDto n2 = new ConnectionRequestEventDto(null, "Maria", "r1");
            assertThat(n1).isEqualTo(n2);
            assertThat(n1).isNotEqualTo(new ConnectionRequestEventDto("t1", "Maria", "r1"));
            assertThat(new ConnectionRequestEventDto("t1", null, "r1"))
                    .isNotEqualTo(new ConnectionRequestEventDto("t1", "Maria", "r1"));
        }

        @Test
        void hashCode_and_toString() {
            ConnectionRequestEventDto d1 = new ConnectionRequestEventDto("t1", "Maria", "r1");
            ConnectionRequestEventDto d2 = new ConnectionRequestEventDto("t1", "Maria", "r1");
            assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
            assertThat(new ConnectionRequestEventDto(null, null, null).hashCode()).isNotZero();
            assertThat(d1.toString()).contains("t1").contains("Maria").contains("r1");
        }

        @Test
        void builder_allFieldsAndToString() {
            String builderStr = ConnectionRequestEventDto.builder()
                    .targetUserId("t1")
                    .requesterUserName("Maria")
                    .requesterId("r1")
                    .toString();
            assertThat(builderStr).isNotNull();
        }
    }

    // =========================================================================
    // ParcheInvitationEventDto
    // =========================================================================
    @Nested
    @DisplayName("ParcheInvitationEventDto")
    class ParcheInvitationEventDtoTests {

        @Test
        void noArgsConstructorAndSetters() {
            ParcheInvitationEventDto dto = new ParcheInvitationEventDto();
            dto.setTargetUserId("target-2");
            dto.setInviterUserName("Carlos");
            dto.setParcheId("parche-99");
            dto.setParcheName("Parche del sábado");

            assertThat(dto.getTargetUserId()).isEqualTo("target-2");
            assertThat(dto.getInviterUserName()).isEqualTo("Carlos");
            assertThat(dto.getParcheId()).isEqualTo("parche-99");
            assertThat(dto.getParcheName()).isEqualTo("Parche del sábado");
        }

        @Test
        void allArgsConstructor() {
            ParcheInvitationEventDto dto =
                    new ParcheInvitationEventDto("t2", "Carlos", "p99", "Parche sáb");
            assertThat(dto.getTargetUserId()).isEqualTo("t2");
            assertThat(dto.getInviterUserName()).isEqualTo("Carlos");
            assertThat(dto.getParcheId()).isEqualTo("p99");
            assertThat(dto.getParcheName()).isEqualTo("Parche sáb");
        }

        @Test
        void equals_sameReference() {
            ParcheInvitationEventDto dto =
                    new ParcheInvitationEventDto("t2", "Carlos", "p99", "Parche");
            assertThat(dto.equals(dto)).isTrue();
        }

        @Test
        void equals_equalObjects() {
            ParcheInvitationEventDto d1 =
                    new ParcheInvitationEventDto("t2", "Carlos", "p99", "Parche");
            ParcheInvitationEventDto d2 =
                    new ParcheInvitationEventDto("t2", "Carlos", "p99", "Parche");
            assertThat(d1).isEqualTo(d2);
        }

        @Test
        void equals_nullAndDifferentType() {
            ParcheInvitationEventDto dto =
                    new ParcheInvitationEventDto("t2", "Carlos", "p99", "Parche");
            assertThat(dto.equals(null)).isFalse();
            assertThat(dto.equals("other")).isFalse();
        }

        @Test
        void equals_differentFieldValues() {
            ParcheInvitationEventDto base =
                    new ParcheInvitationEventDto("t2", "Carlos", "p99", "Parche");
            assertThat(base)
                    .isNotEqualTo(new ParcheInvitationEventDto("tX", "Carlos", "p99", "Parche"));
            assertThat(base)
                    .isNotEqualTo(new ParcheInvitationEventDto("t2", "Pedro", "p99", "Parche"));
            assertThat(base)
                    .isNotEqualTo(new ParcheInvitationEventDto("t2", "Carlos", "pXX", "Parche"));
            assertThat(base)
                    .isNotEqualTo(new ParcheInvitationEventDto("t2", "Carlos", "p99", "Otro"));
        }

        @Test
        void equals_withNullFields() {
            ParcheInvitationEventDto n1 =
                    new ParcheInvitationEventDto(null, "Carlos", "p99", "Parche");
            ParcheInvitationEventDto n2 =
                    new ParcheInvitationEventDto(null, "Carlos", "p99", "Parche");
            assertThat(n1).isEqualTo(n2);
            assertThat(n1).isNotEqualTo(
                    new ParcheInvitationEventDto("t2", "Carlos", "p99", "Parche"));
            assertThat(new ParcheInvitationEventDto("t2", null, "p99", "Parche"))
                    .isNotEqualTo(new ParcheInvitationEventDto("t2", "Carlos", "p99", "Parche"));
        }

        @Test
        void hashCode_and_toString() {
            ParcheInvitationEventDto d1 =
                    new ParcheInvitationEventDto("t2", "Carlos", "p99", "Parche");
            ParcheInvitationEventDto d2 =
                    new ParcheInvitationEventDto("t2", "Carlos", "p99", "Parche");
            assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
            assertThat(new ParcheInvitationEventDto(null, null, null, null).hashCode())
                    .isNotZero();
            assertThat(d1.toString()).contains("t2").contains("Carlos").contains("p99");
        }

        @Test
        void builder_allFieldsAndToString() {
            String builderStr = ParcheInvitationEventDto.builder()
                    .targetUserId("t2")
                    .inviterUserName("Carlos")
                    .parcheId("p99")
                    .parcheName("Parche")
                    .toString();
            assertThat(builderStr).isNotNull();
        }
    }

    // =========================================================================
    // NearbyParcheEventDto
    // =========================================================================
    @Nested
    @DisplayName("NearbyParcheEventDto")
    class NearbyParcheEventDtoTests {

        @Test
        void noArgsConstructorAndSetters() {
            NearbyParcheEventDto dto = new NearbyParcheEventDto();
            dto.setTargetUserId("target-3");
            dto.setParcheId("parche-77");
            dto.setParcheName("Parche en el parque");
            dto.setDistanceKm(1.5);

            assertThat(dto.getTargetUserId()).isEqualTo("target-3");
            assertThat(dto.getParcheId()).isEqualTo("parche-77");
            assertThat(dto.getParcheName()).isEqualTo("Parche en el parque");
            assertThat(dto.getDistanceKm()).isEqualTo(1.5);
        }

        @Test
        void allArgsConstructor() {
            NearbyParcheEventDto dto = new NearbyParcheEventDto("t3", "p77", "Parque", 2.0);
            assertThat(dto.getTargetUserId()).isEqualTo("t3");
            assertThat(dto.getParcheId()).isEqualTo("p77");
            assertThat(dto.getParcheName()).isEqualTo("Parque");
            assertThat(dto.getDistanceKm()).isEqualTo(2.0);
        }

        @Test
        void equals_sameReference() {
            NearbyParcheEventDto dto = new NearbyParcheEventDto("t3", "p77", "Parque", 2.0);
            assertThat(dto.equals(dto)).isTrue();
        }

        @Test
        void equals_equalObjects() {
            NearbyParcheEventDto d1 = new NearbyParcheEventDto("t3", "p77", "Parque", 2.0);
            NearbyParcheEventDto d2 = new NearbyParcheEventDto("t3", "p77", "Parque", 2.0);
            assertThat(d1).isEqualTo(d2);
        }

        @Test
        void equals_nullAndDifferentType() {
            NearbyParcheEventDto dto = new NearbyParcheEventDto("t3", "p77", "Parque", 2.0);
            assertThat(dto.equals(null)).isFalse();
            assertThat(dto.equals("other")).isFalse();
        }

        @Test
        void equals_differentFieldValues() {
            NearbyParcheEventDto base = new NearbyParcheEventDto("t3", "p77", "Parque", 2.0);
            assertThat(base).isNotEqualTo(new NearbyParcheEventDto("tX", "p77", "Parque", 2.0));
            assertThat(base).isNotEqualTo(new NearbyParcheEventDto("t3", "pXX", "Parque", 2.0));
            assertThat(base).isNotEqualTo(new NearbyParcheEventDto("t3", "p77", "Otro", 2.0));
            assertThat(base).isNotEqualTo(new NearbyParcheEventDto("t3", "p77", "Parque", 9.9));
        }

        @Test
        void equals_withNullFields() {
            NearbyParcheEventDto n1 = new NearbyParcheEventDto(null, "p77", "Parque", 2.0);
            NearbyParcheEventDto n2 = new NearbyParcheEventDto(null, "p77", "Parque", 2.0);
            assertThat(n1).isEqualTo(n2);
            assertThat(n1).isNotEqualTo(new NearbyParcheEventDto("t3", "p77", "Parque", 2.0));

            NearbyParcheEventDto distNull =
                    new NearbyParcheEventDto("t3", "p77", "Parque", null);
            assertThat(distNull).isEqualTo(
                    new NearbyParcheEventDto("t3", "p77", "Parque", null));
            assertThat(distNull).isNotEqualTo(
                    new NearbyParcheEventDto("t3", "p77", "Parque", 2.0));
            assertThat(new NearbyParcheEventDto("t3", "p77", "Parque", 2.0))
                    .isNotEqualTo(distNull);
        }

        @Test
        void hashCode_and_toString() {
            NearbyParcheEventDto d1 = new NearbyParcheEventDto("t3", "p77", "Parque", 2.0);
            NearbyParcheEventDto d2 = new NearbyParcheEventDto("t3", "p77", "Parque", 2.0);
            assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
            assertThat(new NearbyParcheEventDto(null, null, null, null).hashCode()).isNotZero();
            assertThat(d1.toString()).contains("t3").contains("p77").contains("Parque");
        }

        @Test
        void builder_allFieldsAndToString() {
            String builderStr = NearbyParcheEventDto.builder()
                    .targetUserId("t3")
                    .parcheId("p77")
                    .parcheName("Parque")
                    .distanceKm(2.0)
                    .toString();
            assertThat(builderStr).isNotNull();
        }
    }
}
