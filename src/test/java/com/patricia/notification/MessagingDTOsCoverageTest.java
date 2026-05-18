package com.patricia.notification;

import com.patricia.notification.entrypoints.messaging.dto.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MessagingDTOsCoverageTest {

    private static final UUID UUID_A  = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID UUID_B  = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID UUID_C  = UUID.fromString("00000000-0000-0000-0000-000000000003");
    private static final UUID UUID_X  = UUID.fromString("00000000-0000-0000-0000-000000000099");
    private static final UUID UUID_R1 = UUID.fromString("00000000-0000-0000-0000-000000000010");
    private static final UUID UUID_R2 = UUID.fromString("00000000-0000-0000-0000-000000000011");
    private static final UUID UUID_P1 = UUID.fromString("00000000-0000-0000-0000-000000000020");
    private static final UUID UUID_P2 = UUID.fromString("00000000-0000-0000-0000-000000000021");
    private static final UUID UUID_PX = UUID.fromString("00000000-0000-0000-0000-000000000098");

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
            OtpVerificationEventDto dto = new OtpVerificationEventDto(UUID_A, "a@b.com", "999");
            assertThat(dto.getEmail()).isEqualTo("a@b.com");
            assertThat(dto.getOtpCode()).isEqualTo("999");
        }

        @Test
        void equals_sameReference() {
            OtpVerificationEventDto dto = new OtpVerificationEventDto(UUID_A, "a@b.com", "111");
            assertThat(dto.equals(dto)).isTrue();
        }

        @Test
        void equals_equalObjects() {
            OtpVerificationEventDto d1 = new OtpVerificationEventDto(UUID_A, "a@b.com", "111");
            OtpVerificationEventDto d2 = new OtpVerificationEventDto(UUID_A, "a@b.com", "111");
            assertThat(d1).isEqualTo(d2);
        }

        @Test
        void equals_nullAndDifferentType() {
            OtpVerificationEventDto dto = new OtpVerificationEventDto(UUID_A, "a@b.com", "111");
            assertThat(dto.equals(null)).isFalse();
            assertThat(dto.equals("other")).isFalse();
        }

        @Test
        void equals_differentFieldValues() {
            OtpVerificationEventDto base = new OtpVerificationEventDto(UUID_A, "a@b.com", "111");
            assertThat(base).isNotEqualTo(new OtpVerificationEventDto(UUID_A, "x@b.com", "111"));
            assertThat(base).isNotEqualTo(new OtpVerificationEventDto(UUID_A, "a@b.com", "999"));
            assertThat(base).isNotEqualTo(new OtpVerificationEventDto(UUID_B, "a@b.com", "111"));
        }

        @Test
        void equals_withNullFields() {
            OtpVerificationEventDto n1 = new OtpVerificationEventDto(UUID_A, null, "111");
            OtpVerificationEventDto n2 = new OtpVerificationEventDto(UUID_A, null, "111");
            OtpVerificationEventDto nn = new OtpVerificationEventDto(UUID_A, "a@b.com", "111");

            assertThat(n1).isEqualTo(n2);
            assertThat(n1).isNotEqualTo(nn);
            assertThat(nn).isNotEqualTo(n1);
            assertThat(new OtpVerificationEventDto(UUID_A, "a@b.com", null))
                    .isNotEqualTo(new OtpVerificationEventDto(UUID_A, "a@b.com", "111"));
        }

        @Test
        void hashCode_consistent() {
            OtpVerificationEventDto d1 = new OtpVerificationEventDto(UUID_A, "a@b.com", "111");
            OtpVerificationEventDto d2 = new OtpVerificationEventDto(UUID_A, "a@b.com", "111");
            assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
            assertThat(new OtpVerificationEventDto(null, null, null).hashCode()).isNotZero();
        }

        @Test
        void toString_containsFields() {
            OtpVerificationEventDto dto = new OtpVerificationEventDto(UUID_A, "a@b.com", "123456");
            assertThat(dto.toString()).contains("a@b.com").contains("123456");
        }

        @Test
        void builder_allFieldsAndToString() {
            String builderStr = OtpVerificationEventDto.builder()
                    .userId(UUID_A)
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
            OtpResendEventDto dto = new OtpResendEventDto(UUID_A, "r@b.com", "777");
            assertThat(dto.getEmail()).isEqualTo("r@b.com");
            assertThat(dto.getOtpCode()).isEqualTo("777");
        }

        @Test
        void equals_sameReference() {
            OtpResendEventDto dto = new OtpResendEventDto(UUID_A, "r@b.com", "777");
            assertThat(dto.equals(dto)).isTrue();
        }

        @Test
        void equals_equalObjects() {
            OtpResendEventDto d1 = new OtpResendEventDto(UUID_A, "r@b.com", "777");
            OtpResendEventDto d2 = new OtpResendEventDto(UUID_A, "r@b.com", "777");
            assertThat(d1).isEqualTo(d2);
        }

        @Test
        void equals_nullAndDifferentType() {
            OtpResendEventDto dto = new OtpResendEventDto(UUID_A, "r@b.com", "777");
            assertThat(dto.equals(null)).isFalse();
            assertThat(dto.equals("other")).isFalse();
        }

        @Test
        void equals_differentFieldValues() {
            OtpResendEventDto base = new OtpResendEventDto(UUID_A, "r@b.com", "777");
            assertThat(base).isNotEqualTo(new OtpResendEventDto(UUID_A, "x@b.com", "777"));
            assertThat(base).isNotEqualTo(new OtpResendEventDto(UUID_A, "r@b.com", "000"));
            assertThat(base).isNotEqualTo(new OtpResendEventDto(UUID_B, "r@b.com", "777"));
        }

        @Test
        void equals_withNullFields() {
            OtpResendEventDto n1 = new OtpResendEventDto(UUID_A, null, "777");
            OtpResendEventDto n2 = new OtpResendEventDto(UUID_A, null, "777");
            OtpResendEventDto nn = new OtpResendEventDto(UUID_A, "r@b.com", "777");

            assertThat(n1).isEqualTo(n2);
            assertThat(n1).isNotEqualTo(nn);
            assertThat(nn).isNotEqualTo(n1);
            assertThat(new OtpResendEventDto(UUID_A, "r@b.com", null))
                    .isNotEqualTo(new OtpResendEventDto(UUID_A, "r@b.com", "777"));
        }

        @Test
        void hashCode_and_toString() {
            OtpResendEventDto d1 = new OtpResendEventDto(UUID_A, "r@b.com", "777");
            OtpResendEventDto d2 = new OtpResendEventDto(UUID_A, "r@b.com", "777");
            assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
            assertThat(new OtpResendEventDto(null, null, null).hashCode()).isNotZero();
            assertThat(d1.toString()).contains("r@b.com").contains("777");
        }

        @Test
        void builder_allFieldsAndToString() {
            String builderStr = OtpResendEventDto.builder()
                    .userId(UUID_A)
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
            dto.setUserId(UUID_A);

            assertThat(dto.getEmail()).isEqualTo("reset@example.com");
            assertThat(dto.getResetCode()).isEqualTo("RESET-001");
            assertThat(dto.getUserId()).isEqualTo(UUID_A);
        }

        @Test
        void allArgsConstructor() {
            PasswordResetEventDto dto = new PasswordResetEventDto("p@b.com", "CODE", UUID_A);
            assertThat(dto.getEmail()).isEqualTo("p@b.com");
            assertThat(dto.getResetCode()).isEqualTo("CODE");
            assertThat(dto.getUserId()).isEqualTo(UUID_A);
        }

        @Test
        void equals_sameReference() {
            PasswordResetEventDto dto = new PasswordResetEventDto("p@b.com", "CODE", UUID_A);
            assertThat(dto.equals(dto)).isTrue();
        }

        @Test
        void equals_equalObjects() {
            PasswordResetEventDto d1 = new PasswordResetEventDto("p@b.com", "CODE", UUID_A);
            PasswordResetEventDto d2 = new PasswordResetEventDto("p@b.com", "CODE", UUID_A);
            assertThat(d1).isEqualTo(d2);
        }

        @Test
        void equals_nullAndDifferentType() {
            PasswordResetEventDto dto = new PasswordResetEventDto("p@b.com", "CODE", UUID_A);
            assertThat(dto.equals(null)).isFalse();
            assertThat(dto.equals("other")).isFalse();
        }

        @Test
        void equals_differentFieldValues() {
            PasswordResetEventDto base = new PasswordResetEventDto("p@b.com", "CODE", UUID_A);
            assertThat(base).isNotEqualTo(new PasswordResetEventDto("x@b.com", "CODE", UUID_A));
            assertThat(base).isNotEqualTo(new PasswordResetEventDto("p@b.com", "DIFF", UUID_A));
            assertThat(base).isNotEqualTo(new PasswordResetEventDto("p@b.com", "CODE", UUID_B));
        }

        @Test
        void equals_withNullFields() {
            PasswordResetEventDto n1 = new PasswordResetEventDto(null, "CODE", UUID_A);
            PasswordResetEventDto n2 = new PasswordResetEventDto(null, "CODE", UUID_A);
            assertThat(n1).isEqualTo(n2);
            assertThat(n1).isNotEqualTo(new PasswordResetEventDto("p@b.com", "CODE", UUID_A));
            assertThat(new PasswordResetEventDto("p@b.com", null, UUID_A))
                    .isNotEqualTo(new PasswordResetEventDto("p@b.com", "CODE", UUID_A));
        }

        @Test
        void hashCode_and_toString() {
            PasswordResetEventDto d1 = new PasswordResetEventDto("p@b.com", "CODE", UUID_A);
            PasswordResetEventDto d2 = new PasswordResetEventDto("p@b.com", "CODE", UUID_A);
            assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
            assertThat(new PasswordResetEventDto(null, null, null).hashCode()).isNotZero();
            assertThat(d1.toString()).contains("p@b.com").contains("CODE");
        }

        @Test
        void builder_allFieldsAndToString() {
            String builderStr = PasswordResetEventDto.builder()
                    .email("p@b.com")
                    .resetCode("CODE")
                    .userId(UUID_A)
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
            dto.setTargetUserId(UUID_A);
            dto.setRequesterUserName("Maria");
            dto.setRequesterId(UUID_R1);

            assertThat(dto.getTargetUserId()).isEqualTo(UUID_A);
            assertThat(dto.getRequesterUserName()).isEqualTo("Maria");
            assertThat(dto.getRequesterId()).isEqualTo(UUID_R1);
        }

        @Test
        void allArgsConstructor() {
            ConnectionRequestEventDto dto = new ConnectionRequestEventDto(UUID_A, "Maria", UUID_R1);
            assertThat(dto.getTargetUserId()).isEqualTo(UUID_A);
            assertThat(dto.getRequesterUserName()).isEqualTo("Maria");
            assertThat(dto.getRequesterId()).isEqualTo(UUID_R1);
        }

        @Test
        void equals_sameReference() {
            ConnectionRequestEventDto dto = new ConnectionRequestEventDto(UUID_A, "Maria", UUID_R1);
            assertThat(dto.equals(dto)).isTrue();
        }

        @Test
        void equals_equalObjects() {
            ConnectionRequestEventDto d1 = new ConnectionRequestEventDto(UUID_A, "Maria", UUID_R1);
            ConnectionRequestEventDto d2 = new ConnectionRequestEventDto(UUID_A, "Maria", UUID_R1);
            assertThat(d1).isEqualTo(d2);
        }

        @Test
        void equals_nullAndDifferentType() {
            ConnectionRequestEventDto dto = new ConnectionRequestEventDto(UUID_A, "Maria", UUID_R1);
            assertThat(dto.equals(null)).isFalse();
            assertThat(dto.equals("other")).isFalse();
        }

        @Test
        void equals_differentFieldValues() {
            ConnectionRequestEventDto base = new ConnectionRequestEventDto(UUID_A, "Maria", UUID_R1);
            assertThat(base).isNotEqualTo(new ConnectionRequestEventDto(UUID_B, "Maria", UUID_R1));
            assertThat(base).isNotEqualTo(new ConnectionRequestEventDto(UUID_A, "Juan", UUID_R1));
            assertThat(base).isNotEqualTo(new ConnectionRequestEventDto(UUID_A, "Maria", UUID_R2));
        }

        @Test
        void equals_withNullFields() {
            ConnectionRequestEventDto n1 = new ConnectionRequestEventDto(null, "Maria", UUID_R1);
            ConnectionRequestEventDto n2 = new ConnectionRequestEventDto(null, "Maria", UUID_R1);
            assertThat(n1).isEqualTo(n2);
            assertThat(n1).isNotEqualTo(new ConnectionRequestEventDto(UUID_A, "Maria", UUID_R1));
            assertThat(new ConnectionRequestEventDto(UUID_A, null, UUID_R1))
                    .isNotEqualTo(new ConnectionRequestEventDto(UUID_A, "Maria", UUID_R1));
        }

        @Test
        void hashCode_and_toString() {
            ConnectionRequestEventDto d1 = new ConnectionRequestEventDto(UUID_A, "Maria", UUID_R1);
            ConnectionRequestEventDto d2 = new ConnectionRequestEventDto(UUID_A, "Maria", UUID_R1);
            assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
            assertThat(new ConnectionRequestEventDto(null, null, null).hashCode()).isNotZero();
            assertThat(d1.toString()).contains(UUID_A.toString()).contains("Maria").contains(UUID_R1.toString());
        }

        @Test
        void builder_allFieldsAndToString() {
            String builderStr = ConnectionRequestEventDto.builder()
                    .targetUserId(UUID_A)
                    .requesterUserName("Maria")
                    .requesterId(UUID_R1)
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
            dto.setTargetUserId(UUID_B);
            dto.setInviterUserName("Carlos");
            dto.setParcheId(UUID_P1);
            dto.setParcheName("Parche del sábado");

            assertThat(dto.getTargetUserId()).isEqualTo(UUID_B);
            assertThat(dto.getInviterUserName()).isEqualTo("Carlos");
            assertThat(dto.getParcheId()).isEqualTo(UUID_P1);
            assertThat(dto.getParcheName()).isEqualTo("Parche del sábado");
        }

        @Test
        void allArgsConstructor() {
            ParcheInvitationEventDto dto =
                    new ParcheInvitationEventDto(UUID_B, "Carlos", UUID_P1, "Parche sáb");
            assertThat(dto.getTargetUserId()).isEqualTo(UUID_B);
            assertThat(dto.getInviterUserName()).isEqualTo("Carlos");
            assertThat(dto.getParcheId()).isEqualTo(UUID_P1);
            assertThat(dto.getParcheName()).isEqualTo("Parche sáb");
        }

        @Test
        void equals_sameReference() {
            ParcheInvitationEventDto dto =
                    new ParcheInvitationEventDto(UUID_B, "Carlos", UUID_P1, "Parche");
            assertThat(dto.equals(dto)).isTrue();
        }

        @Test
        void equals_equalObjects() {
            ParcheInvitationEventDto d1 =
                    new ParcheInvitationEventDto(UUID_B, "Carlos", UUID_P1, "Parche");
            ParcheInvitationEventDto d2 =
                    new ParcheInvitationEventDto(UUID_B, "Carlos", UUID_P1, "Parche");
            assertThat(d1).isEqualTo(d2);
        }

        @Test
        void equals_nullAndDifferentType() {
            ParcheInvitationEventDto dto =
                    new ParcheInvitationEventDto(UUID_B, "Carlos", UUID_P1, "Parche");
            assertThat(dto.equals(null)).isFalse();
            assertThat(dto.equals("other")).isFalse();
        }

        @Test
        void equals_differentFieldValues() {
            ParcheInvitationEventDto base =
                    new ParcheInvitationEventDto(UUID_B, "Carlos", UUID_P1, "Parche");
            assertThat(base)
                    .isNotEqualTo(new ParcheInvitationEventDto(UUID_X, "Carlos", UUID_P1, "Parche"));
            assertThat(base)
                    .isNotEqualTo(new ParcheInvitationEventDto(UUID_B, "Pedro", UUID_P1, "Parche"));
            assertThat(base)
                    .isNotEqualTo(new ParcheInvitationEventDto(UUID_B, "Carlos", UUID_PX, "Parche"));
            assertThat(base)
                    .isNotEqualTo(new ParcheInvitationEventDto(UUID_B, "Carlos", UUID_P1, "Otro"));
        }

        @Test
        void equals_withNullFields() {
            ParcheInvitationEventDto n1 =
                    new ParcheInvitationEventDto(null, "Carlos", UUID_P1, "Parche");
            ParcheInvitationEventDto n2 =
                    new ParcheInvitationEventDto(null, "Carlos", UUID_P1, "Parche");
            assertThat(n1).isEqualTo(n2);
            assertThat(n1).isNotEqualTo(
                    new ParcheInvitationEventDto(UUID_B, "Carlos", UUID_P1, "Parche"));
            assertThat(new ParcheInvitationEventDto(UUID_B, null, UUID_P1, "Parche"))
                    .isNotEqualTo(new ParcheInvitationEventDto(UUID_B, "Carlos", UUID_P1, "Parche"));
        }

        @Test
        void hashCode_and_toString() {
            ParcheInvitationEventDto d1 =
                    new ParcheInvitationEventDto(UUID_B, "Carlos", UUID_P1, "Parche");
            ParcheInvitationEventDto d2 =
                    new ParcheInvitationEventDto(UUID_B, "Carlos", UUID_P1, "Parche");
            assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
            assertThat(new ParcheInvitationEventDto(null, null, null, null).hashCode())
                    .isNotZero();
            assertThat(d1.toString()).contains(UUID_B.toString()).contains("Carlos").contains(UUID_P1.toString());
        }

        @Test
        void builder_allFieldsAndToString() {
            String builderStr = ParcheInvitationEventDto.builder()
                    .targetUserId(UUID_B)
                    .inviterUserName("Carlos")
                    .parcheId(UUID_P1)
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
            dto.setTargetUserId(UUID_C);
            dto.setParcheId(UUID_P2);
            dto.setParcheName("Parche en el parque");
            dto.setDistanceKm(1.5);

            assertThat(dto.getTargetUserId()).isEqualTo(UUID_C);
            assertThat(dto.getParcheId()).isEqualTo(UUID_P2);
            assertThat(dto.getParcheName()).isEqualTo("Parche en el parque");
            assertThat(dto.getDistanceKm()).isEqualTo(1.5);
        }

        @Test
        void allArgsConstructor() {
            NearbyParcheEventDto dto = new NearbyParcheEventDto(UUID_C, UUID_P2, "Parque", 2.0);
            assertThat(dto.getTargetUserId()).isEqualTo(UUID_C);
            assertThat(dto.getParcheId()).isEqualTo(UUID_P2);
            assertThat(dto.getParcheName()).isEqualTo("Parque");
            assertThat(dto.getDistanceKm()).isEqualTo(2.0);
        }

        @Test
        void equals_sameReference() {
            NearbyParcheEventDto dto = new NearbyParcheEventDto(UUID_C, UUID_P2, "Parque", 2.0);
            assertThat(dto.equals(dto)).isTrue();
        }

        @Test
        void equals_equalObjects() {
            NearbyParcheEventDto d1 = new NearbyParcheEventDto(UUID_C, UUID_P2, "Parque", 2.0);
            NearbyParcheEventDto d2 = new NearbyParcheEventDto(UUID_C, UUID_P2, "Parque", 2.0);
            assertThat(d1).isEqualTo(d2);
        }

        @Test
        void equals_nullAndDifferentType() {
            NearbyParcheEventDto dto = new NearbyParcheEventDto(UUID_C, UUID_P2, "Parque", 2.0);
            assertThat(dto.equals(null)).isFalse();
            assertThat(dto.equals("other")).isFalse();
        }

        @Test
        void equals_differentFieldValues() {
            NearbyParcheEventDto base = new NearbyParcheEventDto(UUID_C, UUID_P2, "Parque", 2.0);
            assertThat(base).isNotEqualTo(new NearbyParcheEventDto(UUID_X, UUID_P2, "Parque", 2.0));
            assertThat(base).isNotEqualTo(new NearbyParcheEventDto(UUID_C, UUID_PX, "Parque", 2.0));
            assertThat(base).isNotEqualTo(new NearbyParcheEventDto(UUID_C, UUID_P2, "Otro", 2.0));
            assertThat(base).isNotEqualTo(new NearbyParcheEventDto(UUID_C, UUID_P2, "Parque", 9.9));
        }

        @Test
        void equals_withNullFields() {
            NearbyParcheEventDto n1 = new NearbyParcheEventDto(null, UUID_P2, "Parque", 2.0);
            NearbyParcheEventDto n2 = new NearbyParcheEventDto(null, UUID_P2, "Parque", 2.0);
            assertThat(n1).isEqualTo(n2);
            assertThat(n1).isNotEqualTo(new NearbyParcheEventDto(UUID_C, UUID_P2, "Parque", 2.0));

            NearbyParcheEventDto distNull =
                    new NearbyParcheEventDto(UUID_C, UUID_P2, "Parque", null);
            assertThat(distNull).isEqualTo(
                    new NearbyParcheEventDto(UUID_C, UUID_P2, "Parque", null));
            assertThat(distNull).isNotEqualTo(
                    new NearbyParcheEventDto(UUID_C, UUID_P2, "Parque", 2.0));
            assertThat(new NearbyParcheEventDto(UUID_C, UUID_P2, "Parque", 2.0))
                    .isNotEqualTo(distNull);
        }

        @Test
        void hashCode_and_toString() {
            NearbyParcheEventDto d1 = new NearbyParcheEventDto(UUID_C, UUID_P2, "Parque", 2.0);
            NearbyParcheEventDto d2 = new NearbyParcheEventDto(UUID_C, UUID_P2, "Parque", 2.0);
            assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
            assertThat(new NearbyParcheEventDto(null, null, null, null).hashCode()).isNotZero();
            assertThat(d1.toString()).contains(UUID_C.toString()).contains(UUID_P2.toString()).contains("Parque");
        }

        @Test
        void builder_allFieldsAndToString() {
            String builderStr = NearbyParcheEventDto.builder()
                    .targetUserId(UUID_C)
                    .parcheId(UUID_P2)
                    .parcheName("Parque")
                    .distanceKm(2.0)
                    .toString();
            assertThat(builderStr).isNotNull();
        }
    }
}
