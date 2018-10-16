import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProcedimientoLabsys } from 'app/shared/model/procedimiento-labsys.model';
import { ProcedimientoLabsysService } from './procedimiento-labsys.service';
import { IDetalleOrdenLabsys } from 'app/shared/model/detalle-orden-labsys.model';
import { DetalleOrdenLabsysService } from 'app/entities/detalle-orden-labsys';
import { ITipoMuestraLabsys } from 'app/shared/model/tipo-muestra-labsys.model';
import { TipoMuestraLabsysService } from 'app/entities/tipo-muestra-labsys';

@Component({
    selector: 'jhi-procedimiento-labsys-update',
    templateUrl: './procedimiento-labsys-update.component.html'
})
export class ProcedimientoLabsysUpdateComponent implements OnInit {
    private _procedimiento: IProcedimientoLabsys;
    isSaving: boolean;

    detalleordens: IDetalleOrdenLabsys[];

    tipomuestras: ITipoMuestraLabsys[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private procedimientoService: ProcedimientoLabsysService,
        private detalleOrdenService: DetalleOrdenLabsysService,
        private tipoMuestraService: TipoMuestraLabsysService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ procedimiento }) => {
            this.procedimiento = procedimiento;
        });
        this.detalleOrdenService.query({ filter: 'procedimiento-is-null' }).subscribe(
            (res: HttpResponse<IDetalleOrdenLabsys[]>) => {
                if (!this.procedimiento.detalleOrdenId) {
                    this.detalleordens = res.body;
                } else {
                    this.detalleOrdenService.find(this.procedimiento.detalleOrdenId).subscribe(
                        (subRes: HttpResponse<IDetalleOrdenLabsys>) => {
                            this.detalleordens = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.tipoMuestraService.query().subscribe(
            (res: HttpResponse<ITipoMuestraLabsys[]>) => {
                this.tipomuestras = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.procedimiento.id !== undefined) {
            this.subscribeToSaveResponse(this.procedimientoService.update(this.procedimiento));
        } else {
            this.subscribeToSaveResponse(this.procedimientoService.create(this.procedimiento));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProcedimientoLabsys>>) {
        result.subscribe((res: HttpResponse<IProcedimientoLabsys>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDetalleOrdenById(index: number, item: IDetalleOrdenLabsys) {
        return item.id;
    }

    trackTipoMuestraById(index: number, item: ITipoMuestraLabsys) {
        return item.id;
    }
    get procedimiento() {
        return this._procedimiento;
    }

    set procedimiento(procedimiento: IProcedimientoLabsys) {
        this._procedimiento = procedimiento;
    }
}
