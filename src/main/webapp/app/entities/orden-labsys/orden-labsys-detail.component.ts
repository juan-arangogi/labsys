import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrdenLabsys } from 'app/shared/model/orden-labsys.model';

@Component({
    selector: 'jhi-orden-labsys-detail',
    templateUrl: './orden-labsys-detail.component.html'
})
export class OrdenLabsysDetailComponent implements OnInit {
    orden: IOrdenLabsys;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ orden }) => {
            this.orden = orden;
        });
    }

    previousState() {
        window.history.back();
    }
}
