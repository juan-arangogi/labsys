import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRecipienteLabsys } from 'app/shared/model/recipiente-labsys.model';
import { RecipienteLabsysService } from './recipiente-labsys.service';
import { ITipoMuestraLabsys } from 'app/shared/model/tipo-muestra-labsys.model';
import { TipoMuestraLabsysService } from 'app/entities/tipo-muestra-labsys';

@Component({
    selector: 'jhi-recipiente-labsys-update',
    templateUrl: './recipiente-labsys-update.component.html'
})
export class RecipienteLabsysUpdateComponent implements OnInit {
    private _recipiente: IRecipienteLabsys;
    isSaving: boolean;

    tipomuestras: ITipoMuestraLabsys[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private recipienteService: RecipienteLabsysService,
        private tipoMuestraService: TipoMuestraLabsysService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recipiente }) => {
            this.recipiente = recipiente;
        });
        this.tipoMuestraService.query({ filter: 'recipiente-is-null' }).subscribe(
            (res: HttpResponse<ITipoMuestraLabsys[]>) => {
                if (!this.recipiente.tipoMuestraId) {
                    this.tipomuestras = res.body;
                } else {
                    this.tipoMuestraService.find(this.recipiente.tipoMuestraId).subscribe(
                        (subRes: HttpResponse<ITipoMuestraLabsys>) => {
                            this.tipomuestras = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recipiente.id !== undefined) {
            this.subscribeToSaveResponse(this.recipienteService.update(this.recipiente));
        } else {
            this.subscribeToSaveResponse(this.recipienteService.create(this.recipiente));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRecipienteLabsys>>) {
        result.subscribe((res: HttpResponse<IRecipienteLabsys>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTipoMuestraById(index: number, item: ITipoMuestraLabsys) {
        return item.id;
    }
    get recipiente() {
        return this._recipiente;
    }

    set recipiente(recipiente: IRecipienteLabsys) {
        this._recipiente = recipiente;
    }
}
