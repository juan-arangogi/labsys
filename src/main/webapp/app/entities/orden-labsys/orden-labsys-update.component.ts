import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IOrdenLabsys } from 'app/shared/model/orden-labsys.model';
import { OrdenLabsysService } from './orden-labsys.service';
import { IPacienteLabsys } from 'app/shared/model/paciente-labsys.model';
import { PacienteLabsysService } from 'app/entities/paciente-labsys';
import { IEntidadSaludLabsys } from 'app/shared/model/entidad-salud-labsys.model';
import { EntidadSaludLabsysService } from 'app/entities/entidad-salud-labsys';
import { IMedicoLabsys } from 'app/shared/model/medico-labsys.model';
import { MedicoLabsysService } from 'app/entities/medico-labsys';

@Component({
    selector: 'jhi-orden-labsys-update',
    templateUrl: './orden-labsys-update.component.html'
})
export class OrdenLabsysUpdateComponent implements OnInit {
    private _orden: IOrdenLabsys;
    isSaving: boolean;

    pacientes: IPacienteLabsys[];

    entidadsaluds: IEntidadSaludLabsys[];

    medicos: IMedicoLabsys[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private ordenService: OrdenLabsysService,
        private pacienteService: PacienteLabsysService,
        private entidadSaludService: EntidadSaludLabsysService,
        private medicoService: MedicoLabsysService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ orden }) => {
            this.orden = orden;
        });
        this.pacienteService.query().subscribe(
            (res: HttpResponse<IPacienteLabsys[]>) => {
                this.pacientes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.entidadSaludService.query().subscribe(
            (res: HttpResponse<IEntidadSaludLabsys[]>) => {
                this.entidadsaluds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.medicoService.query().subscribe(
            (res: HttpResponse<IMedicoLabsys[]>) => {
                this.medicos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.orden.id !== undefined) {
            this.subscribeToSaveResponse(this.ordenService.update(this.orden));
        } else {
            this.subscribeToSaveResponse(this.ordenService.create(this.orden));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOrdenLabsys>>) {
        result.subscribe((res: HttpResponse<IOrdenLabsys>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackPacienteById(index: number, item: IPacienteLabsys) {
        return item.id;
    }

    trackEntidadSaludById(index: number, item: IEntidadSaludLabsys) {
        return item.id;
    }

    trackMedicoById(index: number, item: IMedicoLabsys) {
        return item.id;
    }
    get orden() {
        return this._orden;
    }

    set orden(orden: IOrdenLabsys) {
        this._orden = orden;
    }
}
