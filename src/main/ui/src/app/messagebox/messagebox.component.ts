import { Component, OnInit, Input } from '@angular/core';

declare const $: any;


@Component({
    selector: 'app-messagebox',
    templateUrl: './messagebox.component.html',
    styleUrls: ['./messagebox.component.css']
})
export class MessageboxComponent implements OnInit {

    constructor() { }

    ngOnInit() {
    }


    successMessage: string;
    loadingMessage: string;
    errorMessage:string;

    loadingMessageBig = "";

    addMessage(type, message){
        if (!this.loadingMessage)
            this.loadingMessage="";
        if (message == null )
            message="";
        this.loadingMessageBig += message;
        this.loadingMessage = this.loadingMessageBig;
        this.setMessage('loading', this.loadingMessage)
        
    }

    clearMessages(){
       
        // this.successMessage= null;
        // this.errorMessage = null;
        // this.loadingMessage = null;
        // this.loadingMessageBig = "";
        
    }

    setMessage(type, message) {
        if (type=='success'){
            this.successMessage= message;
            this.loadingMessage= null;
            this.errorMessage= null;
            this.loadingMessageBig = "";
        }
        else if  (type=='loading'){
            this.successMessage= null;
            this.loadingMessage = message;
            this.errorMessage= null;
        }
        else if  (type=='error'){
            this.successMessage= null;
            this.loadingMessage= null;
            this.errorMessage = message
            this.loadingMessageBig = "";
        }

        this.showMessageBox(type);
    }

   
    showMessageBox(type:string){
        if (type == 'success'){
            $("#messagebox-loading").fadeTo(1000, 0).slideUp(1000);
            setTimeout(() => {
                $("#messagebox-success").fadeTo(1000, 0).slideUp(1000);

            }, 3000);
        }
        else if (type == 'loading'){
                                                                        //apprare subito il loading
            $("#messagebox-success").fadeTo(1000, 0).slideUp(1000);     //cancella a scomparsa il success
            setTimeout(() => {
                // $("#messagebox-loading").fadeTo(1000, 0).slideUp(1000);   // dopo 3 secondi cancella a scomparsa il loading
    
            }, 3000);
        }
        else if (type == 'error'){
                                                                        //apprare subito l'error
            // $("#messagebox-error").fadeTo(1000, 0).slideUp(1000);
            setTimeout(() => {
                $("#messagebox-error").fadeTo(1000, 0).slideUp(1000);    //dopo 3 secondi cancella a scomparsa l'error
    
            }, 3000);
        }
    }
}
