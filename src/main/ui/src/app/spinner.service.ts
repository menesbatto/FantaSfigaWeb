import { Injectable, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { HttpRequest } from '@angular/common/http';

@Injectable()
export class SpinnerService {

    constructor() { }
    
    public isLoading = new BehaviorSubject<boolean>(false);

    setLoading(value){
        this.isLoading.next(value);
    }
}
