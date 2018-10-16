import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPacienteLabsys } from 'app/shared/model/paciente-labsys.model';

type EntityResponseType = HttpResponse<IPacienteLabsys>;
type EntityArrayResponseType = HttpResponse<IPacienteLabsys[]>;

@Injectable({ providedIn: 'root' })
export class PacienteLabsysService {
    private resourceUrl = SERVER_API_URL + 'api/pacientes';

    constructor(private http: HttpClient) {}

    create(paciente: IPacienteLabsys): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(paciente);
        return this.http
            .post<IPacienteLabsys>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(paciente: IPacienteLabsys): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(paciente);
        return this.http
            .put<IPacienteLabsys>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPacienteLabsys>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPacienteLabsys[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(paciente: IPacienteLabsys): IPacienteLabsys {
        const copy: IPacienteLabsys = Object.assign({}, paciente, {
            fechaNacimiento:
                paciente.fechaNacimiento != null && paciente.fechaNacimiento.isValid() ? paciente.fechaNacimiento.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fechaNacimiento = res.body.fechaNacimiento != null ? moment(res.body.fechaNacimiento) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((paciente: IPacienteLabsys) => {
            paciente.fechaNacimiento = paciente.fechaNacimiento != null ? moment(paciente.fechaNacimiento) : null;
        });
        return res;
    }
}
