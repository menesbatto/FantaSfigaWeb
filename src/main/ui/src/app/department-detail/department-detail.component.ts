import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
//import { HttpClient } from '@angular/common/http';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';



@Component({
  selector: 'app-department-detail',
  template: `
    <p>
      department-detail works!
    </p>
    <h3> You selected department with id = {{departmentId}} </h3>

    <a (click) = "goPrevious()"><< Previous </a>
    <a (click) = "goNext()">Next >></a>

    <div>
      <button (click)= "goToDepartments()"> Back </button>
    </div>

    <div>
    <button (click)= "callServer()"> Chiama server </button>
    <span> {{message}}</span>
    </div>

  `,
  styles: []
})
export class DepartmentDetailComponent implements OnInit {

  constructor(private http: HttpClient,
              private route: ActivatedRoute,
              private router: Router) { }

  public departmentId;


  ngOnInit() {
//    let id = parseInt(this.route.snapshot.paramMap.get("id"));
//    this.departmentId = id;

    this.route.paramMap.subscribe((params: ParamMap) => {
      let id = parseInt(params.get('id'));
      this.departmentId = id;
    });
  } 

  public message = "vuoto";

  callServer(): void{

    this.http.get('/app/api/hello').subscribe(data => {
      console.log('DATA', data);
      this.message = data['message'];
    })

  }

 

  goNext() {
    let nextId = this.departmentId + 1;
    this.router.navigate(['/departments', nextId]);
  }

  goPrevious() {
    let previousId = this.departmentId - 1;
    this.router.navigate(['/departments', previousId]);
  }

  goToDepartments() {
    let selectedId = this.departmentId ? this.departmentId : null;
    //this.router.navigate(['/departments', { id: selectedId}]);

    // Invece di settare una rotta statica, ne metto una a partire da quella corrente

    //Non ti specifico la route come prima ('/departments'), ma ti dico solo quale percorso eseguire ('../'),
    //e come oggetto da cui partire la route attuale presa dal servizio relativeTo

    this.router.navigate(['../', { id: selectedId}], {relativeTo: this.route})
  } 

}
