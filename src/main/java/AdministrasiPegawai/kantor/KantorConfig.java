package AdministrasiPegawai.kantor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class KantorConfig {

    @Bean
    CommandLineRunner commandLineRunner(KantorRepository repository){
        return args -> {
            Kantor harmijay = new Kantor(
                    "Bank Harmijay",
                    "Jl. Dago",
                    "KCP"
            );
            Kantor bima = new Kantor(
                    "Bank Bima",
                    "Jl. Simpang",
                    "KP"
            );

            repository.saveAll(
                    List.of(harmijay, bima)
            );
        };
    }
}
