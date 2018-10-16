import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMedicoLabsys } from 'app/shared/model/medico-labsys.model';

type EntityResponseType = HttpResponse<IMedicoLabsys>;
type EntityArrayResponseType = HttpResponse<IMedicoLabsys[]>;

@Injectable({ providedIn: 'root' })
export class MedicoLabsysService {
    private resourceUrl = SERVER_API_URL + 'api/medicos';

    constructor(private http: HttpClient) {}

    create(medico: IMedicoLabsys): Observable<EntityResponseType> {
        return this.http.post<IMedicoLabsys>(this.resourceUrl, medico, { observe: 'response' });
    }

    update(medico: IMedicoLabsys): Observable<EntityResponseType> {
        return this.http.put<IMedicoLabsys>(this.resourceUrl, medico, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMedicoLabsys>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMedicoLabsys[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
