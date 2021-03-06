package AdministrasiPegawai.kantor;

import AdministrasiPegawai.static_variable.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class KantorService {

    private final KantorRepository kantorRepository;
    private Status status;
    Logger logger = LoggerFactory.getLogger(KantorService.class);

    @Autowired
    public KantorService(KantorRepository kantorRepository, Status status) {
        this.kantorRepository = kantorRepository;
        this.status = status;
    }

    public List<Kantor> getKantors(){
        return kantorRepository.findAll();
    }

    public HashMap<String, Object> validateIdKantor(Long kantorId){
        HashMap<String, Object> response = new HashMap<>();
        Optional<Kantor> kantorOptional = kantorRepository.findKantorById(kantorId);
        if (kantorOptional.isPresent()){
            response.put("status", Status.CODE_ID_KANTOR_FOUND);
            response.put("message", Status.MSG_ID_KANTOR_FOUND);
        } else {
            response.put("status", Status.CODE_ID_KANTOR_NOT_FOUND);
            response.put("message", Status.MSG_ID_KANTOR_NOT_FOUND);
        }
        return response;
    }

    public HashMap<String, Object> addNewKantor(Kantor kantor){
        HashMap<String, Object> response = new HashMap<>();
        Optional<Kantor> kantorOptional = kantorRepository.findKantorByAddress(kantor.getAlamat());
        if (kantorOptional.isPresent()){
            response.put("status", Status.CODE_ADD_KANTOR_FAILED);
            response.put("message", Status.MSG_ADD_KANTOR_FAILED);
        } else {
            kantorRepository.save(kantor);
            response.put("status", Status.CODE_ADD_KANTOR_SUCCESS);
            response.put("message", Status.MSG_ADD_KANTOR_SUCCESS);
        }
        return response;
    }

    public void deleteKantor(Long kantorId){
        boolean kantorExists = kantorRepository.existsById(kantorId);
        if (!kantorExists){
            logger.info("Kantor dengan id" + kantorId + "tidak ada!");
        }
        kantorRepository.deleteById(kantorId);
    }

    @Transactional
    public void updateKantor(Long kantorId, String name, String alamat, String status){
        checkIfUpdateKantorEligible(kantorId, name, alamat, status);
    }

    public void checkIfUpdateKantorEligible(Long kantorId, String name, String alamat, String status){
        Kantor kantor = kantorRepository.findById(kantorId).orElseThrow(() -> new IllegalStateException(
                "Kantor dengan id" + kantorId + "tidak ada!"
        ));

        if (isInputtedDataNull(name) && isInputCharMoreThanZero(name) && !isInputtedDataSame(name,kantor.getName())){
            kantor.setName(name);
        }
        if (isInputtedDataNull(alamat) && isInputCharMoreThanZero(alamat) && !isInputtedDataSame(alamat, kantor.getAlamat())){
            kantor.setAlamat(alamat);
        }
        if (isInputtedDataNull(status) && isInputCharMoreThanZero(status) && !isInputtedDataSame(status, kantor.getStatus())){
            kantor.setStatus(status);
        }
    }

    public boolean isInputtedDataSame(String inputtedData, String checkData){
        return Objects.equals(inputtedData, checkData);
    }

    public boolean isInputtedDataNull(String inputtedData){
        return inputtedData != null;
    }

    public boolean isInputCharMoreThanZero(String inputtedData){
        return inputtedData.length() > 0;
    }
}
