import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpResponse, HttpErrorResponse, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { SpinnerService } from './spinner.service';

import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/finally';

@Injectable()
export class MyLoaderInterceptor implements HttpInterceptor {
    private requests: HttpRequest<any>[] = [];

    constructor(private spinnerService: SpinnerService) { }

    removeRequest(req: HttpRequest<any>) {
        const i = this.requests.indexOf(req);
        this.requests.splice(i, 1);
        
        let areThereHangedRequests = this.requests.length > 0;
        this.spinnerService.setLoading(areThereHangedRequests);
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        this.requests.push(req);
        this.spinnerService.setLoading(true);

        return Observable.create(observer => {
            const subscription = next.handle(req)
                .subscribe(
                    event => {
                        if (event instanceof HttpResponse) {
                            this.removeRequest(req);
                            observer.next(event);
                        }
                    },
                    err => { 
                        this.removeRequest(req); 
                        observer.error(err); 
                    },
                    () => { 
                        this.removeRequest(req); 
                        observer.complete(); 
                    });
            // teardown logic in case of cancelled requests
            return () => {
                this.removeRequest(req);
                subscription.unsubscribe();
            };
        });
    }
}
