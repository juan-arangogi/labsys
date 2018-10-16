import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IPacienteLabsys } from 'app/shared/model/paciente-labsys.model';
import { PacienteLabsysService } from './paciente-labsys.service';

@Component({
    selector: 'jhi-paciente-labsys-update',
    templateUrl: './paciente-labsys-update.component.html'
})
export class PacienteLabsysUpdateComponent implements OnInit {
    private _paciente: IPacienteLabsys;
    isSaving: boolean;
    fechaNacimientoDp: any;

    constructor(private pacienteService: PacienteLabsysService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ paciente }) => {
            this.paciente = paciente;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.paciente.id !== undefined) {
            this.subscribeToSaveResponse(this.pacienteService.update(this.paciente));
        } else {
            this.subscribeToSaveResponse(this.pacienteService.create(this.paciente));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPacienteLabsys>>) {
        result.subscribe((res: HttpResponse<IPacienteLabsys>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get paciente() {
        return this._paciente;
    }

    set paciente(paciente: IPacienteLabsys) {
        this._paciente = paciente;
    }
}
