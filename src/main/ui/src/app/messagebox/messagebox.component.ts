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


    clearMessage(type){
        if (type=='success')
            this.successMessage= null;
        else if  (type=='loading')
            this.loadingMessage = null
    }

    setMessage(type, message) {
        if (type=='success')
            this.successMessage= message;
        else if  (type=='loading')
            this.loadingMessage = message
              
        if (type == 'success'){
            $("#messagebox-loading").fadeTo(1000, 0).slideUp(1000);
            setTimeout(() => {
                $("#messagebox-success").fadeTo(1000, 0).slideUp(1000);

            }, 3000);
        }
        else if (type == 'loading'){
            $("#messagebox-success").fadeTo(1000, 0).slideUp(1000);
            setTimeout(() => {
                // $("#loading-alert").fadeTo(1000, 0).slideUp(1000);
    
            }, 3000);
        }
    }

    // setLoadingMessage(text) {
    //     this.loadingMessage = text;
    //     $("#success-alert").fadeTo(1000, 0).slideUp(1000);
    //     setTimeout(() => {
    //         // $("#loading-alert").fadeTo(1000, 0).slideUp(1000);

    //     }, 3000);
    // }


}
