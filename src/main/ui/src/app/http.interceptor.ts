import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpResponse, HttpErrorResponse, HttpHandler, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Router } from '@angular/router';
import { SpinnerService } from './spinner.service';

import 'rxjs/add/operator/do';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/finally';
import { HeaderService } from './header.service';

@Injectable()
export class MyLoaderInterceptor implements HttpInterceptor {
    private requests: HttpRequest<any>[] = [];

    constructor(
        private spinnerService: SpinnerService,
        private headerService: HeaderService
    ) { }

    removeRequest(req: HttpRequest<any>) {
        const i = this.requests.indexOf(req);
        console.log("REQ1:" + JSON.stringify(req) + "\nARRAY1:" + JSON.stringify( this.requests))
        this.requests.splice(i, 1);
        console.log("REQ2:" + JSON.stringify(req) + "\nARRAY2:" + JSON.stringify( this.requests))

        console.log("---")
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
                        this.headerService.changeErrorMessage(err.status + " - " + err.statusText);
                    },
                    () => { 
                        // this.removeRequest(req); 
                        observer.complete(); 
                    });
            // teardown logic in case of cancelled requests
            // return () => {
            //     this.removeRequest(req);
            //     subscription.unsubscribe();
            // };
        });
    }
}
