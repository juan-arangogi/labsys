import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecipienteLabsys } from 'app/shared/model/recipiente-labsys.model';

@Component({
    selector: 'jhi-recipiente-labsys-detail',
    templateUrl: './recipiente-labsys-detail.component.html'
})
export class RecipienteLabsysDetailComponent implements OnInit {
    recipiente: IRecipienteLabsys;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recipiente }) => {
            this.recipiente = recipiente;
        });
    }

    previousState() {
        window.history.back();
    }
}
