import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEntidadSaludLabsys } from 'app/shared/model/entidad-salud-labsys.model';
import { EntidadSaludLabsysService } from './entidad-salud-labsys.service';

@Component({
    selector: 'jhi-entidad-salud-labsys-update',
    templateUrl: './entidad-salud-labsys-update.component.html'
})
export class EntidadSaludLabsysUpdateComponent implements OnInit {
    private _entidadSalud: IEntidadSaludLabsys;
    isSaving: boolean;

    constructor(private entidadSaludService: EntidadSaludLabsysService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ entidadSalud }) => {
            this.entidadSalud = entidadSalud;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.entidadSalud.id !== undefined) {
            this.subscribeToSaveResponse(this.entidadSaludService.update(this.entidadSalud));
        } else {
            this.subscribeToSaveResponse(this.entidadSaludService.create(this.entidadSalud));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEntidadSaludLabsys>>) {
        result.subscribe((res: HttpResponse<IEntidadSaludLabsys>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get entidadSalud() {
        return this._entidadSalud;
    }

    set entidadSalud(entidadSalud: IEntidadSaludLabsys) {
        this._entidadSalud = entidadSalud;
    }
}
