import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-department-list',
  template: `
    <h3> Department-list</h3>
    <ul class="items">
      <li (click)="onSelect(department)" [class.selected]="isSelected(department)" *ngFor = "let department of departments">
        <span class = "badge"> {{department.id}} </span> {{department.name}}
      </li>
    </ul>
    <span>#####</span>

    <ul class="items">
      <li  *ngFor = "let user of userList">
        <span class = "badge"> {{user.firstname}} {{user.lastname}} {{user.email}} </span>
      </li>
    </ul>

    <span>{{errorMessage}}</span>
  `,
  styles: []
})
export class DepartmentListComponent implements OnInit {

constructor(private router: Router,
            private route: ActivatedRoute,
            private _userService: UserService) { }

public selectedId;
public userList;
public errorMessage = "";

ngOnInit() {
  this._userService.getUsers().subscribe(
          data => this.userList = data,
          error => this.errorMessage = error);

  this.route.paramMap.subscribe((params : ParamMap) => {
    let id = parseInt(params.get('id'));
    this.selectedId = id;
  })
}

onSelect(department){
  //this. router.navigate(['/departments', department.id])

  //Non ti specifico la route come prima ('/departments'), ma ti dico solo quale percorso eseguire ([department.id]),
  //e come oggetto da cui partire la route attuale presa dal servizio relativeTo
  this.router.navigate([department.id],{relativeTo: this.route})
}

isSelected(department){
  return department.id === this.selectedId;

}

departments = [
  {"id":1, "name": "Angular"},
  {"id":2, "name": "Node"},
  {"id":3, "name": "Mongo"},
  {"id":4, "name": "Bootstrap"},
  {"id":5, "name": "Java"},
  {"id":6, "name": "PHP"},
]

 

}
