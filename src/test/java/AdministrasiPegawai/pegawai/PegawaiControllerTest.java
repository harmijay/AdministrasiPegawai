package AdministrasiPegawai.pegawai;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PegawaiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PegawaiService pegawaiService;

    @Test
    public void testGetPegawaiApi() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:7099/api/v1/pegawai"))
                .andDo(print())
                .andExpect(status().is(200));
    }
}
