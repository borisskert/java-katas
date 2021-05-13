package codewarsstylerankingsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    User user;

    @BeforeEach
    public void setup() throws Exception {
        user = new User();
    }

    @Test
    public void shouldBeInitialized() throws Exception {
        assertThat(user.rank).isEqualTo(-8);
        assertThat(user.progress).isEqualTo(0);
    }

    @Nested
    class WhenIncreaseByMinusEight {

        @BeforeEach
        public void setup() throws Exception {
            user.incProgress(-8);
        }

        @Test
        public void shouldHaveProgress() throws Exception {
            assertThat(user.progress).isEqualTo(3);
        }

        @Test
        public void shouldHaveRank() throws Exception {
            assertThat(user.rank).isEqualTo(-8);
        }
    }

    @Nested
    class WhenIncreaseByMinusSeven {

        @BeforeEach
        public void setup() throws Exception {
            user.incProgress(-7);
        }

        @Test
        public void shouldHaveProgress() throws Exception {
            assertThat(user.progress).isEqualTo(10);
        }

        @Test
        public void shouldHaveRank() throws Exception {
            assertThat(user.rank).isEqualTo(-8);
        }

        @Nested
        class WhenIncreaseByMinusFive {

            @BeforeEach
            public void setup() throws Exception {
                user.incProgress(-5);
            }

            @Test
            public void shouldHaveProgress() throws Exception {
                assertThat(user.progress).isEqualTo(0);
            }

            @Test
            public void shouldHaveRank() throws Exception {
                assertThat(user.rank).isEqualTo(-7);
            }
        }
    }

    @Nested
    class WhenIncreaseByMinusSix {

        @BeforeEach
        public void setup() throws Exception {
            user.incProgress(-6);
        }

        @Test
        public void shouldHaveProgress() throws Exception {
            assertThat(user.progress).isEqualTo(40);
        }

        @Test
        public void shouldHaveRank() throws Exception {
            assertThat(user.rank).isEqualTo(-8);
        }
    }

    @Nested
    class WhenIncreaseByMinusFive {

        @BeforeEach
        public void setup() throws Exception {
            user.incProgress(-5);
        }

        @Test
        public void shouldHaveProgress() throws Exception {
            assertThat(user.progress).isEqualTo(90);
        }

        @Test
        public void shouldHaveRank() throws Exception {
            assertThat(user.rank).isEqualTo(-8);
        }
    }

    @Nested
    class WhenIncreaseByMinusFour {

        @BeforeEach
        public void setup() throws Exception {
            user.incProgress(-4);
        }

        @Test
        public void shouldHaveProgress() throws Exception {
            assertThat(user.progress).isEqualTo(60);
        }

        @Test
        public void shouldHaveRank() throws Exception {
            assertThat(user.rank).isEqualTo(-7);
        }
    }

    @Nested
    class WhenIncreaseByMinusThree {

        @BeforeEach
        public void setup() throws Exception {
            user.incProgress(-3);
        }

        @Test
        public void shouldHaveProgress() throws Exception {
            assertThat(user.progress).isEqualTo(50);
        }

        @Test
        public void shouldHaveRank() throws Exception {
            assertThat(user.rank).isEqualTo(-6);
        }
    }

    @Nested
    class WhenIncreaseByMinusTwo {

        @BeforeEach
        public void setup() throws Exception {
            user.incProgress(-2);
        }

        @Test
        public void shouldHaveProgress() throws Exception {
            assertThat(user.progress).isEqualTo(60);
        }

        @Test
        public void shouldHaveRank() throws Exception {
            assertThat(user.rank).isEqualTo(-5);
        }
    }

    @Nested
    class WhenIncreaseByMinusOne {

        @BeforeEach
        public void setup() throws Exception {
            user.incProgress(-1);
        }

        @Test
        public void shouldHaveProgress() throws Exception {
            assertThat(user.progress).isEqualTo(90);
        }

        @Test
        public void shouldHaveRank() throws Exception {
            assertThat(user.rank).isEqualTo(-4);
        }
    }

    @Test
    public void shouldNotAllowIncreaseByZero() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> user.incProgress(0));
    }
}
