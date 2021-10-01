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

    @PostMapping(path = "/register-pegawai/")
    public HashMap<String, Object> registerNewPegawai(@RequestBody Pegawai pegawai) {
        HashMap<String, Object> registerResponse = new HashMap<>();
        HashMap<String, Object> kantorResponse = kantorService.validateIdKantor(pegawai.getIdKantor());
        if (kantorResponse != null) {
            if (kantorResponse.get("message").toString().equals(Status.MSG_ID_KANTOR_FOUND)) {
                return pegawaiService.addNewPegawai(pegawai);
            } else {
                return kantorResponse;
            }
        }else{
            registerResponse.put(Status.KEY_STATUS,"404");
            registerResponse.put(Status.KEY_MESSAGE, Status.MSG_RETRIEVE_KANTOR_INFORMATION_FAILED);
            return registerResponse;
        }
    }

    @DeleteMapping(path = "/delete-pegawai/{pegawaiId}")
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

    @PutMapping(path = "update-pegawai/")
    public void updatePegawai(
            @RequestBody Pegawai pegawai
    ) {
        pegawaiService.updatePegawai(pegawai.getId(), pegawai.getName(), pegawai.getSalary(), pegawai.getPosition());
    }
}
