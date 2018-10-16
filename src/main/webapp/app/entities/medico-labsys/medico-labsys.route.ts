import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MedicoLabsys } from 'app/shared/model/medico-labsys.model';
import { MedicoLabsysService } from './medico-labsys.service';
import { MedicoLabsysComponent } from './medico-labsys.component';
import { MedicoLabsysDetailComponent } from './medico-labsys-detail.component';
import { MedicoLabsysUpdateComponent } from './medico-labsys-update.component';
import { MedicoLabsysDeletePopupComponent } from './medico-labsys-delete-dialog.component';
import { IMedicoLabsys } from 'app/shared/model/medico-labsys.model';

@Injectable({ providedIn: 'root' })
export class MedicoLabsysResolve implements Resolve<IMedicoLabsys> {
    constructor(private service: MedicoLabsysService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((medico: HttpResponse<MedicoLabsys>) => medico.body));
        }
        return of(new MedicoLabsys());
    }
}

export const medicoRoute: Routes = [
    {
        path: 'medico',
        component: MedicoLabsysComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Medicos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'medico/:id/view',
        component: MedicoLabsysDetailComponent,
        resolve: {
            medico: MedicoLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medicos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'medico/new',
        component: MedicoLabsysUpdateComponent,
        resolve: {
            medico: MedicoLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medicos'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'medico/:id/edit',
        component: MedicoLabsysUpdateComponent,
        resolve: {
            medico: MedicoLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medicos'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const medicoPopupRoute: Routes = [
    {
        path: 'medico/:id/delete',
        component: MedicoLabsysDeletePopupComponent,
        resolve: {
            medico: MedicoLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Medicos'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
