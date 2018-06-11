import { Component, TemplateRef } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { BsModalRef, BsModalService } from 'ngx-bootstrap/modal';

@Component({
    selector: 'modal-basic',
    templateUrl: './modal-basic.html'
})
export class ModalBasicComponent {
    public modalRef: BsModalRef; // {1}
    constructor(private modalService: BsModalService) { } // {2}

    public openModal(template: TemplateRef<any>) {
        this.modalRef = this.modalService.show(template); // {3}
    }
}