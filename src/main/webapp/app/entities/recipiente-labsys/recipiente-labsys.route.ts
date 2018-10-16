import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { RecipienteLabsys } from 'app/shared/model/recipiente-labsys.model';
import { RecipienteLabsysService } from './recipiente-labsys.service';
import { RecipienteLabsysComponent } from './recipiente-labsys.component';
import { RecipienteLabsysDetailComponent } from './recipiente-labsys-detail.component';
import { RecipienteLabsysUpdateComponent } from './recipiente-labsys-update.component';
import { RecipienteLabsysDeletePopupComponent } from './recipiente-labsys-delete-dialog.component';
import { IRecipienteLabsys } from 'app/shared/model/recipiente-labsys.model';

@Injectable({ providedIn: 'root' })
export class RecipienteLabsysResolve implements Resolve<IRecipienteLabsys> {
    constructor(private service: RecipienteLabsysService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((recipiente: HttpResponse<RecipienteLabsys>) => recipiente.body));
        }
        return of(new RecipienteLabsys());
    }
}

export const recipienteRoute: Routes = [
    {
        path: 'recipiente',
        component: RecipienteLabsysComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Recipientes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipiente/:id/view',
        component: RecipienteLabsysDetailComponent,
        resolve: {
            recipiente: RecipienteLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipientes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipiente/new',
        component: RecipienteLabsysUpdateComponent,
        resolve: {
            recipiente: RecipienteLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipientes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipiente/:id/edit',
        component: RecipienteLabsysUpdateComponent,
        resolve: {
            recipiente: RecipienteLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipientes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recipientePopupRoute: Routes = [
    {
        path: 'recipiente/:id/delete',
        component: RecipienteLabsysDeletePopupComponent,
        resolve: {
            recipiente: RecipienteLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Recipientes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
