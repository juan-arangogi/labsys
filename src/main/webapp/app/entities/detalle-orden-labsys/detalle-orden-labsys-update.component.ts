import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDetalleOrdenLabsys } from 'app/shared/model/detalle-orden-labsys.model';
import { DetalleOrdenLabsysService } from './detalle-orden-labsys.service';
import { IProcedimientoLabsys } from 'app/shared/model/procedimiento-labsys.model';
import { ProcedimientoLabsysService } from 'app/entities/procedimiento-labsys';
import { IOrdenLabsys } from 'app/shared/model/orden-labsys.model';
import { OrdenLabsysService } from 'app/entities/orden-labsys';

@Component({
    selector: 'jhi-detalle-orden-labsys-update',
    templateUrl: './detalle-orden-labsys-update.component.html'
})
export class DetalleOrdenLabsysUpdateComponent implements OnInit {
    private _detalleOrden: IDetalleOrdenLabsys;
    isSaving: boolean;

    procedimientos: IProcedimientoLabsys[];

    ordens: IOrdenLabsys[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private detalleOrdenService: DetalleOrdenLabsysService,
        private procedimientoService: ProcedimientoLabsysService,
        private ordenService: OrdenLabsysService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ detalleOrden }) => {
            this.detalleOrden = detalleOrden;
        });
        this.procedimientoService.query().subscribe(
            (res: HttpResponse<IProcedimientoLabsys[]>) => {
                this.procedimientos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.ordenService.query().subscribe(
            (res: HttpResponse<IOrdenLabsys[]>) => {
                this.ordens = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.detalleOrden.id !== undefined) {
            this.subscribeToSaveResponse(this.detalleOrdenService.update(this.detalleOrden));
        } else {
            this.subscribeToSaveResponse(this.detalleOrdenService.create(this.detalleOrden));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDetalleOrdenLabsys>>) {
        result.subscribe((res: HttpResponse<IDetalleOrdenLabsys>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackOrdenById(index: number, item: IOrdenLabsys) {
        return item.id;
    }
    get detalleOrden() {
        return this._detalleOrden;
    }

    set detalleOrden(detalleOrden: IDetalleOrdenLabsys) {
        this._detalleOrden = detalleOrden;
    }
}
