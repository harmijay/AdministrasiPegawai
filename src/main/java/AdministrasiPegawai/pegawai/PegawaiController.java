package AdministrasiPegawai.pegawai;

import AdministrasiPegawai.kantor.KantorService;
import AdministrasiPegawai.static_variable.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/pegawai")
public class PegawaiController {

    private final PegawaiService pegawaiService;
    private final KantorService kantorService;

    @Autowired
    private WebClient.Builder webClientBuilder;


    @Autowired
    public PegawaiController(PegawaiService pegawaiService, KantorService kantorService) {
        this.pegawaiService = pegawaiService;
        this.kantorService = kantorService;
    }

    @GetMapping
    public List<Pegawai> getPegawai() {
        return pegawaiService.getPegawai();
    }

    @PostMapping
    public void registerNewPegawai(@RequestBody Pegawai pegawai) {
        HashMap<String, Object> response = kantorService.validateIdKantor(pegawai.getId());
        if (response != null) {
            if (response.get("message").toString().equals(Status.MSG_ID_KANTOR_NOT_FOUND)) {
                pegawaiService.addNewPegawai(pegawai);
            } else {
                throw new IllegalStateException(response.get("message").toString());
            }
        }else{
            throw new IllegalStateException(AdministrasiPegawai.static_variable.Status.MSG_RETRIEVE_KANTOR_INFORMATION_FAILED);
        }
    }

    @DeleteMapping(path = "{pegawaiId}")
    public void deletePegawai(@PathVariable("pegawaiId") Long pegawaiId) {
        pegawaiService.deletePegawai(pegawaiId);
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("nomorNasabah",232);
        data.put("jenisTransaksi",2);
        data.put("waktuTransaksi", LocalDateTime.now());
        data.put("statusTransaksi",1);
        data.put("logTransaksi","Pegawai with id "+pegawaiId+" has been deleted");
        webClientBuilder.build()
                .post()
                .uri("http://localhost:7007/api/transaksi/")
                .body(Mono.just(data),HashMap.class)
                .retrieve()
                .bodyToMono(HashMap.class).block();
    }

    @PutMapping
    public void updatePegawai(
            @RequestBody Pegawai pegawai
    ) {
        pegawaiService.updatePegawai(pegawai.getId(), pegawai.getName(), pegawai.getSalary(), pegawai.getPosition());
    }
}
