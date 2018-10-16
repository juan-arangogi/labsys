import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IMedicoLabsys } from 'app/shared/model/medico-labsys.model';
import { MedicoLabsysService } from './medico-labsys.service';
import { IEntidadSaludLabsys } from 'app/shared/model/entidad-salud-labsys.model';
import { EntidadSaludLabsysService } from 'app/entities/entidad-salud-labsys';

@Component({
    selector: 'jhi-medico-labsys-update',
    templateUrl: './medico-labsys-update.component.html'
})
export class MedicoLabsysUpdateComponent implements OnInit {
    private _medico: IMedicoLabsys;
    isSaving: boolean;

    entidadsaluds: IEntidadSaludLabsys[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private medicoService: MedicoLabsysService,
        private entidadSaludService: EntidadSaludLabsysService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ medico }) => {
            this.medico = medico;
        });
        this.entidadSaludService.query().subscribe(
            (res: HttpResponse<IEntidadSaludLabsys[]>) => {
                this.entidadsaluds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.medico.id !== undefined) {
            this.subscribeToSaveResponse(this.medicoService.update(this.medico));
        } else {
            this.subscribeToSaveResponse(this.medicoService.create(this.medico));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMedicoLabsys>>) {
        result.subscribe((res: HttpResponse<IMedicoLabsys>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEntidadSaludById(index: number, item: IEntidadSaludLabsys) {
        return item.id;
    }
    get medico() {
        return this._medico;
    }

    set medico(medico: IMedicoLabsys) {
        this._medico = medico;
    }
}
