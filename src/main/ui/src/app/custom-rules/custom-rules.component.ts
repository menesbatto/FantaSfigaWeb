import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, ParamMap } from '@angular/router';
import { LeaguesService } from '../leagues.service';
import { Location } from '@angular/common';

@Component({
    selector: 'app-custom-rules',
    template: `
   <div>
        
    
    
        
        <div class="row">
            <div class="row col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="col-lg-11 col-md-11 col-sm-10 col-xs-10 ">
                    <span class= "spanxxl"> Bonus/Malus Calciatori </span>
                </div>
                <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2 pull-right">
                    <a (click) = "showBonusMalus = !showBonusMalus" class="btn btn-sm btn-info">
                        <span *ngIf="showBonusMalus" class="glyphicon glyphicon-chevron-up"> </span>
                        <span *ngIf="!showBonusMalus" class="glyphicon glyphicon-chevron-down"> </span>
                    </a>
                </div>
            </div>
        </div>


       

        <div id="form-step1" class="row" *ngIf="bonusMalus && showBonusMalus">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                
                    <div class="row">
                        <label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label" for="golsegnato">Gol segnato:</label>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="p "><span class="role">P</span> </div>
                                <input name="ctl00$main$golsegnato1" [(ngModel)]="bonusMalus.scoredGoal.P" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" android:inputtype="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="d "><span class="role">D</span> </div>
                                <input name="ctl00$main$golsegnato2" [(ngModel)]="bonusMalus.scoredGoal.D" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="c "><span class="role">C</span> </div>
                                <input name="ctl00$main$golsegnato3" [(ngModel)]="bonusMalus.scoredGoal.C" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="a "><span class="role">A</span> </div>
                                <input name="ctl00$main$golsegnato4" [(ngModel)]="bonusMalus.scoredGoal.A" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                        </div>
                            
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" data-toggle="tooltip"  data-placement="top" title="Tooltip on top" data-original-title="Inserisci per ogni ruolo il valore del gol segnato"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label" for="golsubito">Gol subito:</label>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                        <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                            <div class="p "><span class="role">P</span> </div>
                                <input name="ctl00$main$golsubito1" [(ngModel)]="bonusMalus.takenGoal.P" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="d "><span class="role">D</span> </div>
                                <input name="ctl00$main$golsubito2" [(ngModel)]="bonusMalus.takenGoal.D" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="c "><span class="role">C</span> </div>
                                <input name="ctl00$main$golsubito3" [(ngModel)]="bonusMalus.takenGoal.C" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="a "><span class="role">A</span> </div>
                                <input name="ctl00$main$golsubito4" [(ngModel)]="bonusMalus.takenGoal.A" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del gol subito"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label" for="rigoresegnato">Rigore segnato:</label>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                            <div class="p "><span class="role">P</span> </div>
                                <input name="ctl00$main$rigoresegnato1" [(ngModel)]="bonusMalus.scoredPenalty.P" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="d "><span class="role">D</span> </div>
                                <input name="ctl00$main$rigoresegnato2" [(ngModel)]="bonusMalus.scoredPenalty.D" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="c "><span class="role">C</span> </div>
                                <input name="ctl00$main$rigoresegnato3" [(ngModel)]="bonusMalus.scoredPenalty.C" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="a "><span class="role">A</span> </div>
                                <input name="ctl00$main$rigoresegnato4" [(ngModel)]="bonusMalus.scoredPenalty.A" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del rigore segnato"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label" for="rigoresbagliato">Rigore sbagliato:</label>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="p "><span class="role">P</span> </div>
                                <input name="ctl00$main$rigoresbagliato1" [(ngModel)]="bonusMalus.missedPenalty.P" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="d "><span class="role">D</span> </div>
                                <input name="ctl00$main$rigoresbagliato2" [(ngModel)]="bonusMalus.missedPenalty.D" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="c"><span class="role">C</span> </div>
                                <input name="ctl00$main$rigoresbagliato3" [(ngModel)]="bonusMalus.missedPenalty.C" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="a"><span class="role">A</span> </div>
                                <input name="ctl00$main$rigoresbagliato4" [(ngModel)]="bonusMalus.missedPenalty.A" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del rigore sbagliato"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label" for="rigoreparato">Rigore parato:</label>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="p "><span class="role">P</span> </div>
                                <input name="ctl00$main$rigoreparato1" [(ngModel)]="bonusMalus.savedPenalty.P" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="d"><span class="role">D</span> </div>
                                <input name="ctl00$main$rigoreparato2" [(ngModel)]="bonusMalus.savedPenalty.D" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="c"><span class="role">C</span> </div>
                                <input name="ctl00$main$rigoreparato3" [(ngModel)]="bonusMalus.savedPenalty.C" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="a"><span class="role">A</span> </div>
                                <input name="ctl00$main$rigoreparato4" [(ngModel)]="bonusMalus.savedPenalty.A" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del rigore parato"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label" for="ammonizione">Ammonizione:</label>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div   class="p "><span class="role">P</span> </div>
                                <input name="ctl00$main$ammonizione1" [(ngModel)]="bonusMalus.yellowCard.P" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="d"><span class="role">D</span> </div>
                                <input name="ctl00$main$ammonizione2" [(ngModel)]="bonusMalus.yellowCard.D" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="c"><span class="role">C</span> </div>
                                <input name="ctl00$main$ammonizione3" [(ngModel)]="bonusMalus.yellowCard.C" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="a"><span class="role">A</span> </div>
                                <input name="ctl00$main$ammonizione4" [(ngModel)]="bonusMalus.yellowCard.A" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore dell'ammonizione"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label" for="esplusione">Espulsione:</label>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="p "><span class="role">P</span> </div>
                                <input name="ctl00$main$esplusione1" [(ngModel)]="bonusMalus.redCard.P" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="d"><span class="role">D</span> </div>
                                <input name="ctl00$main$esplusione2" [(ngModel)]="bonusMalus.redCard.D" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="c"><span class="role">C</span> </div>
                                <input name="ctl00$main$esplusione3" [(ngModel)]="bonusMalus.redCard.C" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="a"><span class="role">A</span> </div>
                                <input name="ctl00$main$esplusione4" [(ngModel)]="bonusMalus.redCard.A" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore dell'espulsione"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label" for="assist">Assist:</label>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="p "><span class="role">P</span> </div>
                                <input name="ctl00$main$assist1" [(ngModel)]="bonusMalus.movementAssist.P" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="d"><span class="role">D</span> </div>
                                <input name="ctl00$main$assist2" [(ngModel)]="bonusMalus.movementAssist.D" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="c "><span class="role">C</span> </div>
                                <input name="ctl00$main$assist3" [(ngModel)]="bonusMalus.movementAssist.C" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="a "><span class="role">A</span> </div>
                                <input name="ctl00$main$assist4" [(ngModel)]="bonusMalus.movementAssist.A" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore dell'assist"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label" for="assistfermo">Assist da fermo:</label>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="p "><span class="role">P</span> </div>
                                <input name="ctl00$main$assistfermo1" [(ngModel)]="bonusMalus.stationaryAssist.P" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="d "><span class="role">D</span> </div>
                                <input name="ctl00$main$assistfermo2" [(ngModel)]="bonusMalus.stationaryAssist.D" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="c "><span class="role">C</span> </div>
                                <input name="ctl00$main$assistfermo3" [(ngModel)]="bonusMalus.stationaryAssist.C" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="a "><span class="role">A</span> </div>
                                <input name="ctl00$main$assistfermo4" [(ngModel)]="bonusMalus.stationaryAssist.A" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore dell'assist da fermo (solo redazioni napoli e italia)"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label" for="autogol">Autogol:</label>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="p "><span class="role">P</span> </div>
                                <input name="ctl00$main$autogol1" [(ngModel)]="bonusMalus.autogoal.P" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="d "><span class="role">C</span> </div>
                                <input name="ctl00$main$autogol2" [(ngModel)]="bonusMalus.autogoal.D" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="c "><span class="role">D</span> </div>
                                <input name="ctl00$main$autogol3" [(ngModel)]="bonusMalus.autogoal.C" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="a "><span class="role">A</span> </div>
                                <input name="ctl00$main$autogol4" [(ngModel)]="bonusMalus.autogoal.A" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore dell'autogol"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label" for="golPareggio">Gol decisivo pareggio:</label>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="p "><span class="role">P</span> </div>
                                <input name="ctl00$main$golpareggio1" [(ngModel)]="bonusMalus.evenGoal.P" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="d"><span class="role">D</span> </div>
                                <input name="ctl00$main$golpareggio2" [(ngModel)]="bonusMalus.evenGoal.D" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="c"><span class="role">C</span> </div>
                                <input name="ctl00$main$golpareggio3" [(ngModel)]="bonusMalus.evenGoal.C" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="a"><span class="role">a</span> </div>
                                <input name="ctl00$main$golpareggio4" [(ngModel)]="bonusMalus.evenGoal.A" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del gol decisivo pareggio"></i></div>
                    </div>
                    <div class="row">
                        <label class="col-lg-3 col-md-3 col-sm-3 col-xs-3 control-label" for="golvittoria">Gol decisivo vittoria:</label>
                        <div class="col-lg-8 col-md-8 col-sm-8 col-xs-8">
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="p "><span class="role">P</span> </div>
                                <input name="ctl00$main$golvittoria1" [(ngModel)]="bonusMalus.winGoal.P" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="d "><span class="role">D</span> </div>
                                <input name="ctl00$main$golvittoria2" [(ngModel)]="bonusMalus.winGoal.D" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="c "><span class="role">C</span> </div>
                                <input name="ctl00$main$golvittoria3" [(ngModel)]="bonusMalus.winGoal.C" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-6">
                                <div class="a "><span class="role">A</span> </div>
                                <input name="ctl00$main$golvittoria4" [(ngModel)]="bonusMalus.winGoal.A" class="form-control chk" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                            </div>
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del gol decisivo vittoria"></i></div>
                    </div>
                
            </div>
        </div>




        <br>
        <br>
        <br>
        <br>







































        <div class="row">
            <div class="row col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="col-lg-11 col-md-11 col-sm-10 col-xs-10 ">
                    <span class= "spanxxl"> Fonte Voti e Cartellini </span>
                </div>
                <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2  pull-right">
                    <a (click) = "showDataSource = !showDataSource" class="btn btn-sm btn-info">
                        <span *ngIf="showDataSource" class="glyphicon glyphicon-chevron-up"> </span>
                        <span *ngIf="!showDataSource" class="glyphicon glyphicon-chevron-down"> </span>
                    </a>
                </div>
            </div>
        </div>
        
        <div id="form-step2" class="row" *ngIf="dataSource  && showDataSource">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="row">
                    
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="fontevoti">Fonte voti:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <select name="ctl00$main$ddlfontevoti" [(ngModel)]="dataSource.votesSource"  class="form-control chk">
                            <option value="FANTAGAZZETTA">Fantagazzetta (Ex Napoli)</option>
                            <option value="STATISTICO">Statistico (Alvin482)</option>
                            <option value="ITALIA">Italia</option>
                        </select>
                        
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Seleziona la fonte voti da cui attingere per il calcolo."></i></div>
                </div>
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="fontemb">Fonte bonus/malus:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <select name="ctl00$main$ddlfontebm" id="ddlfontebm" class="form-control chk" disabled="disabled">
                            <option selected="selected" value="1">Fantagazzetta (Ex Napoli)</option>
                        </select>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Fonte bonus/malus Fantagazzetta"></i></div>
                </div>
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="fonteae">Fonte ammonizioni / esplusioni:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <select name="ctl00$main$ddlfonteae" id="ddlfonteae" class="form-control chk" disabled="disabled">
                            <option value="1">Comunicato ufficiale del Giudice Sportivo</option>
                            <option selected="selected" value="2">Fantagazzetta (Ex Napoli)</option>
                        </select>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Seleziona la fonte per le ammonizioni e le espulsioni"></i></div>
                </div>
            </div>
        </div>

        <br>
        <br>
        <br>
        <br>








































        <div class="row">
            <div class="row col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="col-lg-11 col-md-11 col-sm-10 col-xs-10 ">
                    <span class= "spanxxl"> Sostituzioni </span>
                </div>
                <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2 pull-right ">
                    <a (click) = "showSubstitutions = !showSubstitutions" class="btn btn-sm btn-info">
                        <span *ngIf="showSubstitutions" class="glyphicon glyphicon-chevron-up"> </span>
                        <span *ngIf="!showSubstitutions" class="glyphicon glyphicon-chevron-down"> </span>
                    </a>
                </div>
            </div>
        </div>


        <div id="form-step3" class="row" *ngIf="substitutions && showSubstitutions">
             <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="nsostituzioni">Numero sostituzioni:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <input name="ctl00$main$nsostituzioni"  [(ngModel)]="substitutions.substitutionNumber" id="nsostituzioni" class="form-control" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci il numero di sostituzioni da effettuare. Il valore deve essere un intero compreso tra 1 e 11"></i></div>
                </div>
                
                <div id="sosmoduloclassic" class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="priorità">Modalità sostituzioni:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <select name="ctl00$main$ddlsostituzioni"  [(ngModel)]="substitutions.substitutionMode" class="form-control chk"  disabled="disabled">
                            <option value="CHANGE_MODULE">Applicazione immediata del cambio modulo</option>
                            <option value="CHANGE_ROLE_THEN_CHANGE_MODULE">Ruolo per ruolo prioritario sul cambio modulo</option>
                            <option value="CHANGE_ROLE">Solo con cambio ruolo (nessun cambio modulo)</option>
                        </select>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Seleziona la modalità delle sostituzioni"></i></div>
                </div>


                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="amminitosv">Assegna voto ad ammonito sv:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1 val55"><input class="form-check-input" type="radio" [(ngModel)]="substitutions.yellowCardSvOfficeVoteActive" name="yellowCardSvOfficeVoteActive1" [value]="true"></span>Sì </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer2"><input class="form-check-input" type="radio" [(ngModel)]="substitutions.yellowCardSvOfficeVoteActive" name="yellowCardSvOfficeVoteActive2" [value]="false"></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Scegliere se impostare un valore di default per il calciatore ammonito con sv"></i></div>
                </div>


                <div class="row ammonitosv inner nobor" *ngIf="substitutions.yellowCardSvOfficeVoteActive">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="vammonitosv">Valore ammonito sv:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <input name="ctl00$main$vammonitosv" value="0" [(ngModel)]="substitutions.yellowCardSvOfficeVote" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                    </div>
                    <div class="col-lg-2 col-md-1 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserire il valore da attribuire all'ammonito con sv"></i></div>
                </div>


                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="riservaufficiop">Riserva d'ufficio portieri:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1 double"><input class="form-check-input" type="radio" [(ngModel)]="substitutions.goalkeeperPlayerOfficeVoteActive" name="goalkeeperPlayerOfficeVoteActive1" [value]="true" ></span>Sì </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer2 double"><input class="form-check-input" type="radio" [(ngModel)]="substitutions.goalkeeperPlayerOfficeVoteActive" name="goalkeeperPlayerOfficeVoteActive2" [value]="false"></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Scegliere se utilizzare la riserva d'ufficio per i portieri"></i></div>
                </div>

                <div class="row riservaufficiop inner nobor" *ngIf="substitutions.goalkeeperPlayerOfficeVoteActive">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="vriservap">Valore riserva d'ufficio portieri:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <input name="ctl00$main$vriservap" value="3"  [(ngModel)]="substitutions.goalkeeperPlayerOfficeVote" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserire il valore della riserva d'ufficio per i portieri"></i></div>
                </div>


                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="riservaufficiogm">Riserva d'ufficio giocatori di movimento:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1  double"><input class="form-check-input" type="radio" [(ngModel)]="substitutions.movementsPlayerOfficeVoteActive" name="movementsPlayerOfficeVote1" [value]="true"></span>Si </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer2 double"><input class="form-check-input" type="radio" [(ngModel)]="substitutions.movementsPlayerOfficeVoteActive" name="movementsPlayerOfficeVote2" [value]="false" ></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Scegliere se utilizzare la riserva d'ufficio per i giocatori di movimento"></i></div>
                </div>


                <div class="row riservaufficiogm inner nobor" *ngIf="substitutions.movementsPlayerOfficeVoteActive">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="vriservagm">Valore riserva d'ufficio giocatori di movimento:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <input name="ctl00$main$vriservagm" value="4" [(ngModel)]="substitutions.movementsPlayerOfficeVote" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserir eil valore della riserva d'ufficio per i calciatori di movimento"></i></div>
                </div>



                <div class="row innersel" style="display: block;">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="appriserva">Applica riserva d'ufficio fino al:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <select name="ctl00$main$ddlappriserva" [(ngModel)]="substitutions.maxOfficeVotes" class="form-control select">
                            <option value="">Seleziona</option>
                            <option value="TILL_SUBSTITUTIONS">Numero di sostituzioni impostate</option>
                            <option value="TILL_ALL">Raggiungimento degli 11 calciatori</option>
                        </select>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Scegliere se applicare la riserva d'ufficio fino al raggiungimento delle sostituzioni impostate oppure fino a raggiungere il numero di 11 calciatori titolari"></i></div>
                </div>
            </div>
        </div>




        <br>
        <br>
        <br>
        <br>
        






































        <div class="row">
            <div class="row col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="col-lg-11 col-md-11 col-sm-10 col-xs-10 ">
                    <span class= "spanxxl"> Punteggi </span>
                </div>
                <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2 pull-right ">
                    <a (click) = "showPoints = !showPoints" class="btn btn-sm btn-info">
                        <span *ngIf="showPoints" class="glyphicon glyphicon-chevron-up"> </span>
                        <span *ngIf="!showPoints" class="glyphicon glyphicon-chevron-down"> </span>
                    </a>
                </div>
            </div>
        </div>

        <div id="form-step4" class="row" *ngIf="points && showPoints">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="autogol">Soglie Gol:</label>
                    <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del gol decisivo pareggio"></i></div>
                </div>
                <div class="row" >
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="s1">Gol 1:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner" name="s1" [(ngModel)]="points.goalPoints[0]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="s2">Gol 2:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner" name="s2" [(ngModel)]="points.goalPoints[1]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="s3">Gol 3:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner" name="s3" [(ngModel)]="points.goalPoints[2]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="s4">Gol 4:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner" name="s4" [(ngModel)]="points.goalPoints[3]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="s5">Gol 5:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner" name="s5" [(ngModel)]="points.goalPoints[4]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="s6">Gol 6:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner" name="s6" [(ngModel)]="points.goalPoints[5]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="s7">Gol 7:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner" name="s7" [(ngModel)]="points.goalPoints[6]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="s8">Gol 8:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner" name="s8" [(ngModel)]="points.goalPoints[7]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="s9">Gol 9:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner" name="s9" [(ngModel)]="points.goalPoints[8]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="s10">Gol 10:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner" name="s10" [(ngModel)]="points.goalPoints[9]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="s11">Gol 11:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner" name="s11" [(ngModel)]="points.goalPoints[10]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="s12">Gol 12:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner" name="s12" [(ngModel)]="points.goalPoints[11]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                
                <br>
                <br>
    
                <!-- <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="autogol">Punti Formula 1:</label>
                    <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Inserisci per ogni ruolo il valore del gol decisivo pareggio"></i></div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[0]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu1">Punti 1 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu1" [(ngModel)]="points.formulaUnoPoints[0]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[1]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu2">Punti 2 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu2" [(ngModel)]="points.formulaUnoPoints[1]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[2]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu3">Punti 3 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu3" [(ngModel)]="points.formulaUnoPoints[2]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[3]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu4">Punti 4 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu4" [(ngModel)]="points.formulaUnoPoints[3]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[4]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu5">Punti 5 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu5" [(ngModel)]="points.formulaUnoPoints[4]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[5]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu6">Punti 6 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu6" [(ngModel)]="points.formulaUnoPoints[5]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[6]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu7">Punti 7 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu7" [(ngModel)]="points.formulaUnoPoints[6]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[7]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu8">Punti 8 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu8" [(ngModel)]="points.formulaUnoPoints[7]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[8]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu9">Punti 9 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu9" [(ngModel)]="points.formulaUnoPoints[8]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[9]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu10">Punti 10 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu10" [(ngModel)]="points.formulaUnoPoints[9]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[10]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu11">Punti 11 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu11" [(ngModel)]="points.formulaUnoPoints[10]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[11]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu12">Punti 12 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu12" [(ngModel)]="points.formulaUnoPoints[11]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[12]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu13">Punti 13 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu13" [(ngModel)]="points.formulaUnoPoints[12]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[13]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu14">Punti 14 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu14" [(ngModel)]="points.formulaUnoPoints[13]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[14]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu15">Punti 15 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu15" [(ngModel)]="points.formulaUnoPoints[14]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>
                <div class="row" *ngIf = "points.formulaUnoPoints[15]">
                    <label class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="fu16">Punti 16 classificato:</label>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
                        <input class="form-control textinner"  name="fu16" [(ngModel)]="points.formulaUnoPoints[15]" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                </div>-->

                <br>
                <br>


                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="fasciaintorno">Fascia con intorno:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="points.fasciaConIntornoActive" name="fasciaConIntornoActive1" [value]="true" ></span>Sì </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer2"><input class="form-check-input" type="radio" [(ngModel)]="points.fasciaConIntornoActive" name="fasciaConIntornoActive2" [value]="false" ></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Se due squadre totalizzano un punteggio all'interno della stessa fascia, si aggiunge un gol alla squadra che avra' distaccato l'avversario del valore specificato."></i></div>
                </div>
                <div class="row inner nobor" *ngIf="points.fasciaConIntornoActive">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="valintorno">Valore per fascia con intorno:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <input name="ctl00$main$valintorno" [(ngModel)]="points.fasciaConIntorno" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare il valore della fascia con intorno"></i></div>
                </div>

                




                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="intorno">Intorno:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="points.intornoActive" name="intornoActive1" [value]="true" ></span>Sì </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="points.intornoActive" name="intornoActive2" [value]="false" ></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Se i punteggi delle due squadre cadono in fasce differenti, vince chi distacca l'avversario di almeno del valore specificato"></i></div>
                </div>
                <div class="row inner nobor" *ngIf="points.intornoActive">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="vintorno">Valore per intorno:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <input name="ctl00$main$vintorno" [(ngModel)]="points.intorno" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare il valore dell'intorno"></i></div>
                </div>



                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="cpareggio">Controlla pareggio:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="points.controllaPareggioActive" name="controllaPareggioActive1" [value]="true" ></span>Sì </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="points.controllaPareggioActive" name="controllaPareggioActive2" [value]="false" ></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Se due squadre ottengono un punteggio inferiore a Soglia gol specificare lo scarto per far scattare il gol alla squadra con punteggio maggiore"></i></div>
                </div>
                <div class="row inner nobor" *ngIf="points.controllaPareggioActive">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="vcpareggio">Valore per controlla pareggio:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <input name="ctl00$main$vcpareggio" [(ngModel)]="points.controllaPareggio" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare il valore del controlla pareggio"></i></div>
                </div>



                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="dpunti">Differenza punti:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="points.differenzaPuntiActive" name="differenzaPuntiActive1" [value]="true" ></span>Sì </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="points.differenzaPuntiActive" name="differenzaPuntiActive2" [value]="false" ></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Se la differenza dei punti-squadra è uguale o superiore al valore impostato, si attribuisce un altro gol alla squadra con piu' punti"></i></div>
                </div>
                <div class="row inner nobor" *ngIf="points.differenzaPuntiActive">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="vdpunti">Valore per differenza punti:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <input name="ctl00$main$vdpunti" [(ngModel)]="points.differenzaPunti" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare il valore della differenza punti"></i></div>
                </div>



                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="dautogol">Autogol:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="points.autogolActive" name="autogolActive1" [value]="true" ></span>Sì </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="points.autogolActive" name="autogolActive2" [value]="false" ></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Se una delle due squadre ottiene un punteggio inferiore al valore impostato, si attribuisce un altro gol alla squadra avversaria"></i></div>
                </div>
                <div class="row inner nobor" *ngIf="points.autogolActive">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="dautogol">Valore autogol:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <input name="ctl00$main$dautogol" [(ngModel)]="points.autogol" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?">
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare il valore dell'autogol"></i></div>
                </div>



                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="pimbattuto">Bonus portiere imbattuto:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="points.portiereImbattutoActive" name="portiereImbattutoActive1" [value]="true" ></span>Sì </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="points.portiereImbattutoActive" name="portiereImbattutoActive2" [value]="false" ></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare su Si per attribuire un bonus al portiere che non subisce gol. Il portiere deve eaver giocato almeno 25 minuti"></i></div>
                </div>
                <div class="row inner nobor" *ngIf="points.portiereImbattutoActive">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="vpimbattuto">Valore per bonus portiere imbattuto:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <input name="ctl00$main$vpimbattuto" [(ngModel)]="points.portiereImbattuto" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare il valore del bonus da assegnare al portiere imbattuto"></i></div>
                </div>


            </div>
        </div>






        <br>
        <br>
        <br>
        <br>
        



































        <div class="row">
            <div class="row col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="col-lg-11 col-md-11 col-sm-10 col-xs-10 ">
                    <span class= "spanxxl" >Modificatori </span>
                </div>
                <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2 pull-right ">
                    <a (click) = "showModifiers = !showModifiers" class="btn btn-sm btn-info">
                        <span *ngIf="showModifiers" class="glyphicon glyphicon-chevron-up"> </span>
                        <span *ngIf="!showModifiers" class="glyphicon glyphicon-chevron-down"> </span>
                    </a>
                </div>
            </div>
        </div>



        <div id="form-step5" class="row" *ngIf="modifiers && showModifiers">

            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="mportiere">Modificatore portiere:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="modifiers.goalkeeperModifierActive" name="goalkeeperModifierActive1" [value]="true" ></span>Sì </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="modifiers.goalkeeperModifierActive" name="goalkeeperModifierActive2" [value]="false" ></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare su Si per abilitare il calcolo del modificatore portiere"></i></div>
                </div>
                <div id="vmpor" class="row nobor modificatorerow inner disappear"  *ngIf="modifiers.goalkeeperModifierActive">
                    <div class="nobor">
                        <em>Il modificatore del portiere è dato dal voto del portiere schierato in campo, calcolato sulla base dei voti ottenuti in pagella senza considerare bonus e malus.</em>
                    </div>
                    <strong>Punteggio</strong>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se il portiere ha un <strong>voto &lt;=3</strong>, si aggiunge</div>
                        <div class="col-lg-2 col-md-4 col-sm-4 col-xs-4">
                            <input name="ctl00$main$p1" [(ngModel)]="modifiers.goalkeeperVote3" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> al totale dell'avversario</div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se il portiere ha come <strong>voto 3,5</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$p2" [(ngModel)]="modifiers.goalkeeperVote3half" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> al totale dell'avversario</div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se il portiere ha come <strong>voto4</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$p3" [(ngModel)]="modifiers.goalkeeperVote4" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> al totale dell'avversario</div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se il portiere ha come <strong>voto 4,5</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$p4" [(ngModel)]="modifiers.goalkeeperVote4half" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> al totale dell'avversario</div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se il portiere ha come <strong>voto 5</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$p5" [(ngModel)]="modifiers.goalkeeperVote5" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> al totale dell'avversario</div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se il portiere ha come <strong>voto 5,5</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$p6" [(ngModel)]="modifiers.goalkeeperVote5half" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> al totale dell'avversario</div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se il portiere ha come <strong>voto 6</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$p7" [(ngModel)]="modifiers.goalkeeperVote6" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> al totale dell'avversario</div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se il portiere ha come <strong>voto 6,5</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$p8" [(ngModel)]="modifiers.goalkeeperVote6half" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> al totale dell'avversario</div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se il portiere ha come <strong>voto 7</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$p9" [(ngModel)]="modifiers.goalkeeperVote7" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5  col-md-5  col-sm-5 col-xs-9  control-label"><strong>punti</strong> al totale dell'avversario</div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se il portiere ha come <strong>voto 7,5</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$p10" [(ngModel)]="modifiers.goalkeeperVote7half" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> al totale dell'avversario</div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se il portiere ha come <strong>voto 8</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$p11" [(ngModel)]="modifiers.goalkeeperVote8" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label">punti al totale dell'avversario</div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se il portiere ha come <strong>voto 8,5</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$p12" [(ngModel)]="modifiers.goalkeeperVote8half" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> al totale dell'avversario</div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se il portiere ha come <strong>voto &gt;=9</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$p13" [(ngModel)]="modifiers.goalkeeperVote9" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> al totale dell'avversario</div>
                    </div>
                    <div class="nobor">
                        <em>Nel caso nessun portiere porti punteggio alla squadra, non sarà dato nessun bonus o malus alla squadra avversaria.</em>
                    </div>
                </div>

                <br>
                <br>
                
                <div id="mdif" class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="mdifesa">Modificatore difesa:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="modifiers.defenderModifierActive" name="defenderModifierActive1" [value]="true" ></span>Sì </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="modifiers.defenderModifierActive" name="defenderModifierActive2" [value]="false" ></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare su Si per abilitare il calcolo del modificatore difesa"></i></div>
                </div>
                <div id="vmdif" class="row nobor modificatorerow inner disappear" *ngIf="modifiers.defenderModifierActive">
                    <div class="nobor">
                        <em>Il modificatore della difesa è un bonus che si calcola solo se il portiere e almeno 4 difensori portano punteggio alla squadra. Si considerano il voto in pagella del portiere e i tre migliori voti in pagella ottenuti dai difensori, si calcola la media aritmetica di questi quattro valori.</em>
                    </div>
                    <strong>Punteggio</strong>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se la <strong>media è &gt;=7</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$d6" [(ngModel)]="modifiers.defenderAvgVote6" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong></div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se la <strong>media è &gt;=6,5 e &lt;7</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$d6h" [(ngModel)]="modifiers.defenderAvgVote6half" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong></div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-4 col-md-4 col-sm-5 col-xs-12 control-label">se la <strong>media è &gt;=6 e &lt;6,5</strong> si aggiunge</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$d7" [(ngModel)]="modifiers.defenderAvgVote7" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong></div>
                    </div>
                </div>

                <br>
                <br>
                
                <div id="mcen" class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="mcentrocampo">Modificatore centrocampo:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="modifiers.middlefielderModifierActive" name="middlefielderModifierActive1" [value]="true" ></span>Sì </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="modifiers.middlefielderModifierActive" name="middlefielderModifierActive2" [value]="false" ></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare su Si per abilitare il calcolo del modificatore centrocampo"></i></div>
                </div>
                <div id="vmcen" class="row nobor modificatorerow inner  disappear" *ngIf = "modifiers.middlefielderModifierActive">
                    <div class="nobor">
                        <em>Il modificatore del centrocampo è dato dal confronto tra il totale dei centrocampisti delle due squadre, calcolati sulla base dei voti assegnati ai singoli centrocampisti (esclusi bonus e malus).
                        </em>
                    </div>
                    <strong>Punteggio</strong>
                    <div class="row nobor">
                        <p class="col-lg-12 col-md-12 col-sm-12 col-xs-12 control-label">se la <strong>differenza</strong> tra i centrocampisti è <strong>&lt;2</strong>, si assegna:</p>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$m0" [(ngModel)]="modifiers.middlefielderNear0" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> a entrambe le squadre</div>
                    </div>
                    <div class="row nobor">
                        <p class="col-lg-12 col-md-12 col-sm-12 col-xs-12 control-label">se la <strong>differenza</strong> tra i centrocampisti è <strong>compresa tra 2 e 3,99</strong> si assegna:</p>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$mp2" [(ngModel)]="modifiers.middlefielderOver2" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> alla squadra con totale centrocampo migliore e</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$mm2" [(ngModel)]="modifiers.middlefielderUnderMinus2" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 control-label"><strong>punti</strong> all'altra</div>
                    </div>
                    <div class="row nobor">
                        <p class="col-lg-12 col-md-12 col-sm-12 col-xs-12 control-label">se la <strong>differenza</strong> tra i centrocampisti è <strong>compresa tra 4 e 5,99</strong> si assegna:</p>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$mp4" [(ngModel)]="modifiers.middlefielderOver4" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> alla squadra con totale centrocampo migliore e</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$mm4" [(ngModel)]="modifiers.middlefielderUnderMinus4" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 control-label"><strong>punti</strong> all'altra</div>
                    </div>
                    <div class="row nobor">
                        <p class="col-lg-12 col-md-12 col-sm-12 col-xs-12 control-label">se la <strong>differenza</strong> tra i centrocampisti è <strong>compresa tra 6 e 7,99</strong> si assegna:</p>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$mp6" [(ngModel)]="modifiers.middlefielderOver6" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> alla squadra con totale centrocampo migliore e</div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$mm6" [(ngModel)]="modifiers.middlefielderUnderMinus6" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-2 col-md-2  col-sm-2 col-xs-2 control-label"><strong>punti</strong>  all'altra</div>
                    </div>
                    <div class="row nobor">
                        <p class="col-lg-12 col-md-12 col-sm-12 col-xs-12 control-label">se la <strong>differenza</strong> tra i centrocampisti è <strong>&gt;=8</strong> si assegna:</p>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$mp8" [(ngModel)]="modifiers.middlefielderOver8" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-5 col-md-5 col-sm-5 col-xs-9 control-label"><strong>punti</strong> alla squadra con totale centrocampo migliore e </div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$mm8" [(ngModel)]="modifiers.middlefielderUnderMinus8" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 control-label"><strong>punti</strong> all'altra</div>
                    </div>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 nobor">
                        <em>Nel caso di disparità nel numero di centrocampisti schierati dai due fantallenatori, verranno aggiunti alla squadra con meno centrocampisti, tanti voti pari a 5 quanti sono i centrocampisti mancanti per pareggiare il numero dell'avversario.</em>
                    </div>
                </div>

                <br>
                <br>
                
                <div id="matt" class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="mattacco">Modificatore attacco:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="modifiers.strikerModifierActive" name="strikerModifierActive1" [value]="true" ></span>Si </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="modifiers.strikerModifierActive" name="strikerModifierActive2" [value]="false" ></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare su Si per abilitare il calcolo del modificatore attacco"></i></div>
                </div>
                <div id="vmatt" class="row nobor modificatorerow inner disappear" *ngIf="modifiers.strikerModifierActive">
                    <div class="nobor">
                        <em>Il modificatore incide solo ed esclusivamente sugli attaccanti che ricevono voto sufficiente, che non segnano e non sbagliano rigori.</em>
                    </div>
                    <strong>Punteggio</strong>
                    <div class="row nobor">
                        <div class="col-lg-6 col-md-6 col-sm-5 col-xs-12 control-label">
                            se un attaccante prende <strong>6</strong> il suo modificatore dell'attacco è pari a
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$s6" [(ngModel)]="modifiers.strikerVote6" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                        </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-3"><strong>punti</strong></div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-6 col-md-6 col-sm-5 col-xs-12 control-label">
                            se un attaccante prende <strong>6,5</strong> il modificatore dell'attacco è pari a
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$s6h" [(ngModel)]="modifiers.strikerVote6half" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">                            </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-3"><strong>punti</strong></div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-6 col-md-6 col-sm-5 col-xs-12 control-label">
                            se un attaccante prende <strong>7</strong> il modificatore dell'attacco è pari a
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$s7" [(ngModel)]="modifiers.strikerVote7" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">                            </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-3"><strong>punti</strong></div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-6 col-md-6 col-sm-5 col-xs-12 control-label">
                            se un attaccante prende <strong>7,5</strong> il modificatore dell'attacco è pari a
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$s8" [(ngModel)]="modifiers.strikerVote7half" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">                            </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-3"><strong>punti</strong></div>
                    </div>
                    <div class="row nobor">
                        <div class="col-lg-6 col-md-6 col-sm-5 col-xs-12 control-label">
                            se un attaccante prende <strong>8</strong> o più il modificatore dell'attacco è pari a
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                            <input name="ctl00$main$s8" [(ngModel)]="modifiers.strikerVote8" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">                            </div>
                        <div class="col-lg-1 col-md-1 col-sm-1 col-xs-3">
                            <strong>punti</strong>
                        </div>
                    </div>
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 nobor">
                        <em>Il modificatore dell'attacco della squadra equivale alla somma algebrica dei modificatori dell'attacco dei singoli giocatori.</em>
                    </div>
                </div>

                <br>
                <br>


                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="mfairplay">Modificatore FairPlay:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="modifiers.fairPlayModifierActive" name="fairPlayModifierActive1" [value]="true" ></span>Si </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="modifiers.fairPlayModifierActive" name="fairPlayModifierActive2" [value]="false" ></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare su Si per abilitare il calcolo del modificatore fairplay"></i></div>
                </div>
                <div class="row inner nobor disappear" *ngIf="modifiers.fairPlayModifierActive">
                        <div class="nobor">
                        <em>Il modificatore FairPlay aggiunge il bonus alla squadra che non riceve nessuna ammonizione/espulsione ai giocatori che portano punteggio.</em>
                    </div>
                    <label class="col-lg-4 col-md-4 col-sm-4 col-xs-4 control-label" for="dfairplay">Punteggio FairPlay:</label>
                    <div class="col-lg-2 col-md-2 col-sm-4 col-xs-4">
                        <input name="ctl00$main$f" [(ngModel)]="modifiers.fairPlay" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                    </div>
                    <div class="col-lg-2 col-md-1 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare il valore del modificatore FairPlay"></i></div>
                </div>

                <br>
                <br>

                
                <div class="row">
                    <label class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="mfairplay">Modificatore di rendimento:</label>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="modifiers.performanceModifierActive" name="performanceModifierActive1" [value]="true" ></span>Si </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="modifiers.performanceModifierActive" name="performanceModifierActive2" [value]="false" ></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-1 col-xs-1"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare su Si per abilitare il modificatore di rendimento."></i></div>
                </div>
                <div class="row nobor modificatorerow inner disappear" *ngIf="modifiers.performanceModifierActive">
                    <div class="nobor">
                        <em>Il modificatore di rendimento incide solo ed esclusivamente sui calciatori che ricevono voto sufficiente.</em>
                    </div>
                    <strong>Punteggio</strong>
                    <div class="nobor">se 11 calciatori ricevono un voto uguale o maggiore di 6 il modificatore di rendimento è pari a <strong>5 punti</strong></div>
                    <div class="nobor">se 10 calciatori ricevono un voto uguale o maggiore di 6 il modificatore di rendimento è pari a <strong>3 punti</strong></div>
                    <div class="nobor">se 9 calciatori ricevono un voto uguale o maggiore di 6 il modificatore di rendimento è pari a <strong>2 punti</strong></div>
                    <div class="nobor">se 8 calciatori ricevono un voto uguale o maggiore di 6 il modificatore di rendimento è pari a <strong>1 punti</strong></div>
                    <div class="nobor">se 7 calciatori ricevono un voto uguale o maggiore di 6 il modificatore di rendimento è pari a <strong>0 punti</strong></div>
                    <div class="nobor">se 6 calciatori ricevono un voto uguale o maggiore di 6 il modificatore di rendimento è pari a <strong>0 punti</strong></div>
                    <div class="nobor">se 5 calciatori ricevono un voto uguale o maggiore di 6 il modificatore di rendimento è pari a <strong>0 punti</strong></div>
                    <div class="nobor">se 4 calciatori ricevono un voto uguale o maggiore di 6 il modificatore di rendimento è pari a <strong>0 punti</strong></div>
                    <div class="nobor">se 3 calciatori ricevono un voto uguale o maggiore di 6 il modificatore di rendimento è pari a <strong>-1 punti</strong></div>
                    <div class="nobor">se 2 calciatori ricevono un voto uguale o maggiore di 6 il modificatore di rendimento è pari a <strong>-2 punti</strong></div>
                    <div class="nobor">se 1 calciatori ricevono un voto uguale o maggiore di 6 il modificatore di rendimento è pari a <strong>-3 punti</strong></div>
                    <div class="nobor">se 0 calciatori ricevono un voto uguale o maggiore di 6 il modificatore di rendimento è pari a <strong>-5 punti</strong></div>
                </div>
            </div>
        </div>

        <br>
        <br>
        <br>
        <br>






































        <div class="row">
            <div class="row col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="col-lg-11 col-md-11 col-sm-10 col-xs-10 ">
                    <span class= "spanxxl"> Altre Regole </span>
                </div>
                <div class="col-lg-1 col-md-1 col-sm-2 col-xs-2 pull-right ">
                    <a (click) = "showCompetitionRules = !showCompetitionRules" class="btn btn-sm btn-info">
                        <span *ngIf="showCompetitionRules" class="glyphicon glyphicon-chevron-up"> </span>
                        <span *ngIf="!showCompetitionRules" class="glyphicon glyphicon-chevron-down"> </span>
                    </a>
                </div>
            </div>
        </div>



        <div id="form-step6" class="row" *ngIf="competitionRules && showCompetitionRules">
            <div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="row">
                    <label [ngClass]="{ 'diff': competitionRules.homeBonusActive != realRules.competitionRules.homeBonusActive }"
                        class="col-lg-6 col-md-6 col-sm-6 col-xs-6 control-label" for="dautogol">Bonus Casalingo Attivo:</label>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                        <div class="radio" >
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="competitionRules.homeBonusActive" name="homeBonusActive1" [value]="true" ></span>Sì </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                        <div class="radio">
                            <label><span class="outer1"><input class="form-check-input" type="radio" [(ngModel)]="competitionRules.homeBonusActive" name="homeBonusActive2" [value]="false" ></span>No </label>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="La squadra che gioca in casa riceve un bonus"></i></div>
                </div>
                <div class="row inner nobor" *ngIf="competitionRules.homeBonusActive">
                    <label [ngClass]="{ 'diff': competitionRules.homeBonus != realRules.competitionRules.homeBonus }"
                        class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="dautogol">Bonus Casalingo:</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <input name="ctl00$main$vriservap" [(ngModel)]="competitionRules.homeBonus" class="form-control textinner" type="text"  pattern="[0-9]+([\.,][0-9]+)?" step="0.5">
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Impostare il valore del bonus per la squadra che gioca in casa"></i></div>
                </div>
                <div id="sosmoduloclassic" class="row">
                    <label [ngClass]="{ 'diff': competitionRules.postponementBehaviour != realRules.competitionRules.postponementBehaviour }"
                    class="col-lg-6 col-md-6 col-sm-4 col-xs-4 control-label" for="priorità">Rinvio o sospensione delle partite</label>
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <select (change)="behaviourChanged(competitionRules.postponementBehaviour)" name="ctl00$main$postponementBehaviour"  [(ngModel)]="competitionRules.postponementBehaviour" class="form-control chk">
                            <option value="ALL_6"> Tutti 6 </option>
                            <option value="WAIT_MATCHES"> Attesa dei recuperi </option>
                            <option value = "MIXED" hidden> Misto </option>
                        </select>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"><i class="info-ico" rel="tooltip" title="" data-original-title="Seleziona se attendere i recuperi o mettere tutti 6"></i></div>
                </div>
                <br>
                <br>

                <div class="row nobor ">
                    <div class="col-lg-2 col-md-2 col-sm-1 col-xs-1">
                        <div> <strong>#</strong> </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                        <div> <strong>Casa </strong></div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                        <div> <strong>Fuori</strong> </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                        <div><strong>Giocata</strong> </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                        <div> <strong>Voti</strong> </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2"></div>
                </div>
                <div *ngFor="let key of getKeys(map)">
                    <div class="row nobor"  
                    *ngFor = "let postponement of getList(key)">
                    <div class="col-lg-2 col-md-2 col-sm-1 col-xs-1">
                            <div>{{postponement.seasonDay}}</div>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                            <div>{{postponement.homeTeam}}</div>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                            <div>{{postponement.awayTeam}}</div>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                            <div>{{postponement.played ? 'SI' : 'NO'}}</div>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                            <div>{{postponement.wait ? 'ATTESA' : 'TUTTI 6'}}</div>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
                            <div><button  class="btn btn-primary" (click)="invertWait(postponement)"> Cambia </button></div>
                        </div>
                    </div>
                </div>
            
            </div>
        </div>

      

        <br>
        <br>
        
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 alert alert-success" *ngIf="successMessage">
            <strong>{{successMessage}}</strong>
        </div>
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 alert alert-danger" *ngIf="errorMessage">
            <strong>{{errorMessage}}</strong>
        </div>
        <br>
        <br>

        <div class= "row nobor">
            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6 col-lg-offset-4 col-md-offset-4 col-sm-offset-3 col-xs-offset-3" >
                <button class="btn btn-primary  btn-block" (click) = "saveCustomRules()"> Salva </button>
            </div>
        </div>
      
        <div class= "row nobor">
            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6 col-lg-offset-4 col-md-offset-4 col-sm-offset-3 col-xs-offset-3" >
                <button class="btn btn-primary  btn-block" (click) = "resetCustomRules()">  Reset delle regole </button>
            </div>
        </div>

      
        <br>
        <br>
        <br>
        <br>



      

        

    </div>
  `,
    styles: []
})
export class CustomRulesComponent implements OnInit {

    errorMessage = null;
    successMessage = null;


    leagueShortName = null;
    competitionShortName = null;
    competitionBean = {
        leagueShortName: null,
        competitionShortName: null
    };
   
    realRules = null;
   
    bonusMalus: any = null;
    dataSource: any = null;
    points: any = null;
    substitutions: any = null;
    modifiers: any = null;
    competitionRules: any = null;






    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private leagueService: LeaguesService,
        private _location: Location,

    ) { }

    ngOnInit() {
        this.route.paramMap.subscribe((params: ParamMap) => {
            let url1 = params.get('competition');
            this.competitionShortName = url1;

            let url2 = params.get('league');
            this.leagueShortName = url2;

        });

        this.retrieveCustomRules();
    }


    retrieveCustomRules() {
        let retrieveRulesReq = {
            competition: {
                leagueShortName: this.leagueShortName,
                competitionShortName: this.competitionShortName
            }
         }
        
        this.leagueService.retrieveRules(retrieveRulesReq)
            .subscribe(
                data => {
                    this.successMessage = "Le regole sono state recuperate";
                    this.errorMessage = null;
                    
                    this.realRules= data.realRules;

                    this.bonusMalus = data.customRules.bonusMalus;
                    this.dataSource = data.customRules.dataSource;
                    this.points = data.customRules.points;
                    this.substitutions = data.customRules.substitutions;
                    this.modifiers = data.customRules.modifiers;
                    this.competitionRules = data.customRules.competitionRules;

                    this.handlePostponements();

                },

                error => {
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server"
                }

            )


    }

    resetCustomRules(){
        let req = {
            leagueShortName : this.leagueShortName,
            competitionShortName : this.competitionShortName,
            rules : { 
                bonusMalus: this.realRules.bonusMalus,
                dataSource: this.realRules.dataSource,
                points: this.realRules.points,
                substitutions: this.realRules.substitutions,
                modifiers: this.realRules.modifiers,
                competitionRules: this.realRules.competitionRules,
                type : "CUSTOM"
            }    
        }
        this.leagueService.saveCustomRules(req)
        .subscribe(
            data => {
                this.bonusMalus = data.bonusMalus;
                this.dataSource = data.dataSource;
                this.points = data.points;
                this.substitutions = data.substitutions;
                this.modifiers = data.modifiers;
                this.competitionRules = data.competitionRules;
                this.handlePostponements();

                this.successMessage = "Le regole custom sono state resettate";
                this.errorMessage = null;
            },

            error => {
                this.successMessage = null;
                this.errorMessage = "Errore di comunicazione col server"
            });
    }

    saveCustomRules() {
        this.competitionBean.leagueShortName = this.leagueShortName;
        this.competitionBean.competitionShortName = this.competitionShortName;
        
        let req = {
            leagueShortName : this.leagueShortName,
            competitionShortName : this.competitionShortName,
            rules : { 
                bonusMalus: this.bonusMalus,
                dataSource: this.dataSource,
                points: this.points,
                substitutions: this.substitutions,
                modifiers: this.modifiers,
                competitionRules: this.competitionRules,
                type : "CUSTOM"
            }    
        }

        this.leagueService.saveCustomRules(req)
            .subscribe(
                data => {
                    this.successMessage = "Le regole sono state salvate";
                    this.errorMessage = null;
                    this._location.back();
                },

                error => {
                    this.successMessage = null;
                    this.errorMessage = "Errore di comunicazione col server"
                });

        this.errorMessage = null;
    }


    handlePostponements(){
        let mapPost = this.competitionRules.postponementMap;
        for (var property in mapPost) {
            if (mapPost.hasOwnProperty(property)) {
                this.map.set(property, mapPost[property]);
            }
        };
                   
    }

    behaviourChanged(behaviour){
      
        this.map.forEach((value: string, key: string) => {
            for (var i = 0; i< value.length; i++){
                if (behaviour == "ALL_6")
                    value[i]["wait"]= false;
                else if (behaviour == "WAIT_MATCHES")
                    value[i]["wait"]= true;
            }
             
        });
    }
    
    invertWait(postponement){
        postponement.wait = !postponement.wait;
        let isAll6 = false;
        let isWait = false;
        this.map.forEach((value: string, key: string) => {
           for (var i = 0; i< value.length; i++){
                if (value[i]["wait"])    
                    isWait = true;
                else 
                    isAll6 = true;
           }
            
        });
        if (isWait && !isAll6)
            this.competitionRules.postponementBehaviour = "WAIT_MATCHES";
        else if (!isWait && isAll6)
            this.competitionRules.postponementBehaviour = "ALL_6";
        else if (isWait && isAll6)
            this.competitionRules.postponementBehaviour = "MIXED";

    }

    getList(key){
        return this.map.get(key);
    }

    map = new Map<String, String>();

    getKeys(map){
        return Array.from(map.keys());
    }



    goToCompetition(statsType){
        this. router.navigate(['/competition', {league : this.leagueShortName, competition : this.competitionShortName, type:statsType}])
    }

    

}
