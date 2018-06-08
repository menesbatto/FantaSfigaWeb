import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-table',
  template: `
  
    <div class="table-responsive" *ngIf= "rankingIn" >
        <table class="table table-striped">
            <thead class="thead-light">
                <tr>
                    <th>Pos</th>
                    <th>Squadra</th>
                    <th *ngIf= "rankingIn.rows[0].points">Punti</th>
                    <th *ngIf= "rankingIn.rows[0].scoredGoals">Gol Fatti</th>
                    <th *ngIf= "rankingIn.rows[0].takenGoals">Gol Subiti</th>
                    <th *ngIf= "rankingIn.rows[0].sumAllVotes">Somma punteggio</th>
                    <th *ngIf= "rankingIn.rows[0].positions && rankingIn.rows[0].positions[0] != undefined">1</th>
                    <th *ngIf= "rankingIn.rows[0].positions && rankingIn.rows[0].positions[1] != undefined">2</th>
                    <th *ngIf= "rankingIn.rows[0].positions && rankingIn.rows[0].positions[2] != undefined">3</th>
                    <th *ngIf= "rankingIn.rows[0].positions && rankingIn.rows[0].positions[3] != undefined">4</th>
                    <th *ngIf= "rankingIn.rows[0].positions && rankingIn.rows[0].positions[4] != undefined">5</th>
                    <th *ngIf= "rankingIn.rows[0].positions && rankingIn.rows[0].positions[5] != undefined">6</th>
                    <th *ngIf= "rankingIn.rows[0].positions && rankingIn.rows[0].positions[6] != undefined">7</th>
                    <th *ngIf= "rankingIn.rows[0].positions && rankingIn.rows[0].positions[7] != undefined">8</th>
                    <th *ngIf= "rankingIn.rows[0].luckyEdge">Sculate</th>
                    <th *ngIf= "rankingIn.rows[0].luckyEdge">Punti Sculate</th>
                    <th *ngIf= "rankingIn.rows[0].luckyEdge">Sfighe</th>
                    <th *ngIf= "rankingIn.rows[0].luckyEdge">Punti persi</th>
                </tr>
            </thead>
            <tbody>
                <tr *ngFor = "let team of rankingIn.rows; index as i; ">
                    <td>{{i+1}}</td>
                    <td>{{team.name}}</td>
                    <td *ngIf= "team.points">{{team.points | number:'0.0-3'}}</td>
                    <td *ngIf= "team.scoredGoals">{{team.scoredGoals}}</td>
                    <td *ngIf= "team.takenGoals">{{team.takenGoals}}</td>
                    <td *ngIf= "team.sumAllVotes">{{team.sumAllVotes}}</td>
                    
                    <td *ngIf= "team.positions && team.positions[0] != undefined">
                        <button *ngIf="team.bestPosition== 1" class="btn btn-primary btn-block" (click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[0]}}</button>
                        <button *ngIf="team.worstPosition== 1" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[0]}}</button>
                        <span *ngIf="team.bestPosition!=1 && team.worstPosition!=1">{{team.positions[0]}}</span>
                    </td>
                    
                    <td *ngIf= "team.positions && team.positions[1] != undefined">
                        <button *ngIf="team.bestPosition== 2" class="btn btn-primary btn-block"(click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[1]}}</button>
                        <button *ngIf="team.worstPosition== 2" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[1]}}</button>
                        <span *ngIf="team.bestPosition!=2 && team.worstPosition!=2">{{team.positions[1]}}</span>
                    </td>
                    
                    <td *ngIf= "team.positions && team.positions[2] != undefined">
                        <button *ngIf="team.bestPosition==3" class="btn btn-primary btn-block"(click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[2]}}</button>
                        <button *ngIf="team.worstPosition== 3" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[2]}}</button>
                        <span *ngIf="team.bestPosition!=3 && team.worstPosition!=3">{{team.positions[2]}}</span>
                    </td>

                    <td *ngIf= "team.positions && team.positions[3] != undefined">
                        <button *ngIf="team.bestPosition== 4" class="btn btn-primary btn-block"(click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[3]}}</button>
                        <button *ngIf="team.worstPosition== 4" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[3]}}</button>
                        <span *ngIf="team.bestPosition!=4 && team.worstPosition!=4">{{team.positions[3]}}</span>
                    </td>

                    <td *ngIf= "team.positions && team.positions[4] != undefined">
                        <button *ngIf="team.bestPosition== 5" class="btn btn-primary btn-block"(click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[4]}}</button>
                        <button *ngIf="team.worstPosition== 5" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[4]}}</button>
                        <span *ngIf="team.bestPosition!=5 && team.worstPosition!=5">{{team.positions[4]}}</span>
                    </td>

                    <td *ngIf= "team.positions && team.positions[5] != undefined">
                        <button *ngIf="team.bestPosition== 6" class="btn btn-primary btn-block"(click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[5]}}</button>
                        <button *ngIf="team.worstPosition== 6" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[5]}}</button>
                        <span *ngIf="team.bestPosition!=6 && team.worstPosition!=6">{{team.positions[5]}}</span>
                    </td>

                    <td *ngIf= "team.positions && team.positions[6] != undefined">
                        <button *ngIf="team.bestPosition== 7" class="btn btn-primary btn-block"(click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[6]}}</button>
                        <button *ngIf="team.worstPosition== 7" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[6]}}</button>
                        <span *ngIf="team.bestPosition!=7 && team.worstPosition!=7">{{team.positions[6]}}</span>
                    </td>

                    <td *ngIf= "team.positions && team.positions[7] != undefined">
                        <button *ngIf="team.bestPosition== 8" class="btn btn-primary btn-block"(click) = "goToSeason(team.bestPattern, team.name)">{{team.positions[7]}}</button>
                        <button *ngIf="team.worstPosition== 8" class="btn btn-primary btn-block"(click) = "goToSeason(team.worstPattern, team.name)">{{team.positions[7]}}</button>
                        <span *ngIf="team.bestPosition!=8 && team.worstPosition!=8">{{team.positions[7]}}</span>
                    </td>
                    
                    
                    <td *ngIf= "team.luckyEdge">{{team.luckyEdge.luckyEdgeNumber}}</td>
                    <td *ngIf= "team.luckyEdge">{{team.luckyEdge.luckyEdgeGain}}</td>
                    <td *ngIf= "team.luckyEdge">{{team.luckyEdge.unluckyEdgeNumber}}</td>
                    <td *ngIf= "team.luckyEdge">{{team.luckyEdge.unluckyEdgeLose}}</td>
                </tr>
            </tbody>
        </table>
    </div>
    


  `,
  styles: []
})
export class TableComponent implements OnInit {

  
  ngOnInit() {
  }

  @Input()
  public rankingIn:any;

  @Output()
  public goToSeasonClicked: EventEmitter<any>;

  constructor() { 
      this.goToSeasonClicked = new EventEmitter();
  }

  goToSeason = function(worstPattern:string, name:string){
      let req = {
        pattern:worstPattern,
        team:name
      }
      this.goToSeasonClicked.emit(req);
 
  }


}
