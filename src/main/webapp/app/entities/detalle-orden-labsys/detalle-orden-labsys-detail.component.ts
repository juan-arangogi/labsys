import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetalleOrdenLabsys } from 'app/shared/model/detalle-orden-labsys.model';

@Component({
    selector: 'jhi-detalle-orden-labsys-detail',
    templateUrl: './detalle-orden-labsys-detail.component.html'
})
export class DetalleOrdenLabsysDetailComponent implements OnInit {
    detalleOrden: IDetalleOrdenLabsys;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ detalleOrden }) => {
            this.detalleOrden = detalleOrden;
        });
    }

    previousState() {
        window.history.back();
    }
}
