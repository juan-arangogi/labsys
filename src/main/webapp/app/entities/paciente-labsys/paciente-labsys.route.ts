import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PacienteLabsys } from 'app/shared/model/paciente-labsys.model';
import { PacienteLabsysService } from './paciente-labsys.service';
import { PacienteLabsysComponent } from './paciente-labsys.component';
import { PacienteLabsysDetailComponent } from './paciente-labsys-detail.component';
import { PacienteLabsysUpdateComponent } from './paciente-labsys-update.component';
import { PacienteLabsysDeletePopupComponent } from './paciente-labsys-delete-dialog.component';
import { IPacienteLabsys } from 'app/shared/model/paciente-labsys.model';

@Injectable({ providedIn: 'root' })
export class PacienteLabsysResolve implements Resolve<IPacienteLabsys> {
    constructor(private service: PacienteLabsysService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((paciente: HttpResponse<PacienteLabsys>) => paciente.body));
        }
        return of(new PacienteLabsys());
    }
}

export const pacienteRoute: Routes = [
    {
        path: 'paciente',
        component: PacienteLabsysComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Pacientes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'paciente/:id/view',
        component: PacienteLabsysDetailComponent,
        resolve: {
            paciente: PacienteLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pacientes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'paciente/new',
        component: PacienteLabsysUpdateComponent,
        resolve: {
            paciente: PacienteLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pacientes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'paciente/:id/edit',
        component: PacienteLabsysUpdateComponent,
        resolve: {
            paciente: PacienteLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pacientes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pacientePopupRoute: Routes = [
    {
        path: 'paciente/:id/delete',
        component: PacienteLabsysDeletePopupComponent,
        resolve: {
            paciente: PacienteLabsysResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Pacientes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
