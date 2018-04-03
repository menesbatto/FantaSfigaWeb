import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EmployeeListComponent } from './employee-list/employee-list.component';
import { DepartmentListComponent } from './department-list/department-list.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { DepartmentDetailComponent } from './department-detail/department-detail.component';

const routes: Routes = [
  
  {path: '', redirectTo: '/departments-list', pathMatch : "full" },
  {path: 'departments-list', component: DepartmentListComponent},
  {path: 'employees', component: EmployeeListComponent},
  {path: 'departments-list/:id', component: DepartmentDetailComponent},
  {path: '**', component: PageNotFoundComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [DepartmentListComponent, 
                                  DepartmentDetailComponent,
                                  EmployeeListComponent, 
                                  PageNotFoundComponent];
