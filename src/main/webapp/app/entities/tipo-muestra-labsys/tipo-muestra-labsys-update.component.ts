import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ITipoMuestraLabsys } from 'app/shared/model/tipo-muestra-labsys.model';
import { TipoMuestraLabsysService } from './tipo-muestra-labsys.service';
import { IProcedimientoLabsys } from 'app/shared/model/procedimiento-labsys.model';
import { ProcedimientoLabsysService } from 'app/entities/procedimiento-labsys';
import { IRecipienteLabsys } from 'app/shared/model/recipiente-labsys.model';
import { RecipienteLabsysService } from 'app/entities/recipiente-labsys';

@Component({
    selector: 'jhi-tipo-muestra-labsys-update',
    templateUrl: './tipo-muestra-labsys-update.component.html'
})
export class TipoMuestraLabsysUpdateComponent implements OnInit {
    private _tipoMuestra: ITipoMuestraLabsys;
    isSaving: boolean;

    procedimientos: IProcedimientoLabsys[];

    recipientes: IRecipienteLabsys[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private tipoMuestraService: TipoMuestraLabsysService,
        private procedimientoService: ProcedimientoLabsysService,
        private recipienteService: RecipienteLabsysService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tipoMuestra }) => {
            this.tipoMuestra = tipoMuestra;
        });
        this.procedimientoService.query({ filter: 'tipomuestra-is-null' }).subscribe(
            (res: HttpResponse<IProcedimientoLabsys[]>) => {
                if (!this.tipoMuestra.procedimientoId) {
                    this.procedimientos = res.body;
                } else {
                    this.procedimientoService.find(this.tipoMuestra.procedimientoId).subscribe(
                        (subRes: HttpResponse<IProcedimientoLabsys>) => {
                            this.procedimientos = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.recipienteService.query().subscribe(
            (res: HttpResponse<IRecipienteLabsys[]>) => {
                this.recipientes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tipoMuestra.id !== undefined) {
            this.subscribeToSaveResponse(this.tipoMuestraService.update(this.tipoMuestra));
        } else {
            this.subscribeToSaveResponse(this.tipoMuestraService.create(this.tipoMuestra));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITipoMuestraLabsys>>) {
        result.subscribe((res: HttpResponse<ITipoMuestraLabsys>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackProcedimientoById(index: number, item: IProcedimientoLabsys) {
        return item.id;
    }

    trackRecipienteById(index: number, item: IRecipienteLabsys) {
        return item.id;
    }
    get tipoMuestra() {
        return this._tipoMuestra;
    }

    set tipoMuestra(tipoMuestra: ITipoMuestraLabsys) {
        this._tipoMuestra = tipoMuestra;
    }
}
