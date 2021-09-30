package AdministrasiPegawai.kantor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "kantor")
public class KantorController {

    private final KantorService kantorService;

    @Autowired
    public KantorController(KantorService kantorService) {
        this.kantorService = kantorService;
    }

    @GetMapping
    public List<Kantor> getKantors(){
        return kantorService.getKantors();
    }

    @PostMapping (path = "/register-kantor")
    public HashMap<String, Object> registerNewKantor(@RequestBody Kantor kantor){
       HashMap<String, Object> dataKantor = kantorService.addNewKantor(kantor);
       HashMap<String, Object> addKantor = new HashMap<>();

       addKantor.put("Id Kantor Baru : ", kantor.getId());
       addKantor.put("Nama Kantor Baru : ", kantor.getName());
       addKantor.put("Alamat Kantor Baru : ", kantor.getAlamat());
       addKantor.put("Status Kantor Baru : ", kantor.getStatus());

        WebClient client = WebClient.create("http://localhost:7007");
        WebClient.ResponseSpec responseSpec = client.post().uri("/api/transaksi")
                .body(Mono.just(addKantor), HashMap.class)
                .retrieve();

        responseSpec.bodyToMono(HashMap.class).block();
        return dataKantor;
    }

    @GetMapping (path = "/validasi-id-kantor/{kantorId}")
    @ResponseBody
    public HashMap<String, Object> validateIdKantor(@PathVariable Long kantorId){
        return kantorService.validateIdKantor(kantorId);
    }

    @DeleteMapping(path = "/delete-kantor/{kantorId}")
    public void deleteKantor(@PathVariable("kantorId") Long id){
        kantorService.deleteKantor(id);
    }

    @PutMapping(path = "/update-kantor/{kantorId}")
    public void updateKantor(
            @PathVariable("kantorId") Long kantorId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String alamat,
            @RequestParam(required = false) String status) {
        kantorService.updateKantor(kantorId, name, alamat, status);
    }
}
