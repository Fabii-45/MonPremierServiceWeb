package fr.orleans.m1.wsi.monpremierwebservice.controleur;

import fr.orleans.m1.wsi.monpremierwebservice.modele.Etudiant;
import fr.orleans.m1.wsi.monpremierwebservice.modele.FacadePromotion;
import fr.orleans.m1.wsi.monpremierwebservice.modele.exception.EtudiantInexistantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping(value = "/mpws", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controleur {

    @Autowired
    FacadePromotion facadePromotion;

    @PostMapping(value = "/etudiant")
    public ResponseEntity<String> enregistrerEtudiant(@RequestParam String nom, @RequestParam String prenom, @RequestParam String adresse, UriComponentsBuilder base) {
        try {
            String numEtudiant = facadePromotion.enregistrerEtudiant(nom,prenom,adresse);
            return ResponseEntity.status(HttpStatus.CREATED).header("Location", base.toUriString()+"/mpws/etudiant/"+numEtudiant).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/etudiant/{numEtudiant}")
    public ResponseEntity<Etudiant> getEtudiantById(@PathVariable("numEtudiant") String numeroEtudiant){
        try {
            return ResponseEntity.ok(facadePromotion.getEtudiantById(numeroEtudiant));
        } catch (EtudiantInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/etudiant")
    public ResponseEntity<Collection<Etudiant>> getEtudiants(){
        try {
            return ResponseEntity.ok(facadePromotion.getEtudiants());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
