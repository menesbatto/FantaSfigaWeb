import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-postponements',
    templateUrl: './postponements.component.html',
    styles: []
})
export class PostponementsComponent implements OnInit {

    constructor() { 
      
    }

    ngOnInit() {
        let mapPost = this.competitionRules.postponementMap;
        for (var property in mapPost) {
            if (mapPost.hasOwnProperty(property)) {
                this.map.set(property, mapPost[property]);
            }
        };
    }

    @Input()
    competitionRules:any;
    

    map = new Map<String, String>();
    seasonDayToJump = null;

    errorMessage = null;
    successMessage = null;


    modalText:string;
    updateModalText(text:string){
        this.modalText = text; 
    }


    getList(key) {
        //console.log(this.map.get(key));
        let app =this.map.get(key);
        return app;
    }

    

    getKeys(map) {
        let app = map.keys();
        return Array.from(app);
    }

    behaviourChanged(behaviour) {

        this.map.forEach((value: string, key: string) => {
            for (var i = 0; i < value.length; i++) {
                if (behaviour == "ALL_6")
                    value[i]["wait"] = false;
                else if (behaviour == "WAIT_MATCHES")
                    value[i]["wait"] = true;
            }

        });
    }

    invertWait(postponement){
        postponement.wait = !postponement.wait;
        let isAll6 = false;
        let isWait = false;
        this.map.forEach((value: string, key: string) => {
           for (var i = 0; i< value.length; i++){
                if (value[i]["wait"])    
                    isWait = true;
                else 
                    isAll6 = true;
                
           }
            
        });
        if (isWait && !isAll6)
            this.competitionRules.postponementBehaviour = "WAIT_MATCHES";
        else if (!isWait && isAll6)
            this.competitionRules.postponementBehaviour = "ALL_6";
        else if (isWait && isAll6)
            this.competitionRules.postponementBehaviour = "MIXED";

    }

    

    addSeasonDayToJump(){
        if (this.competitionRules.seasonDaysToJump.indexOf(this.seasonDayToJump) < 0
                && this.seasonDayToJump>0 
                && this.seasonDayToJump<38) {
            this.competitionRules.seasonDaysToJump.push(this.seasonDayToJump);
            this.errorMessage = null;
        }
        else {
            this.successMessage= null;
            this.errorMessage = "Inserisci una giornata di Serie A corretta";
        }
    }

    resetSeasonDaysToJump(){
        this.competitionRules.seasonDaysToJump = [];
    }



   

}
