import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecipienteLabsys } from 'app/shared/model/recipiente-labsys.model';

type EntityResponseType = HttpResponse<IRecipienteLabsys>;
type EntityArrayResponseType = HttpResponse<IRecipienteLabsys[]>;

@Injectable({ providedIn: 'root' })
export class RecipienteLabsysService {
    private resourceUrl = SERVER_API_URL + 'api/recipientes';

    constructor(private http: HttpClient) {}

    create(recipiente: IRecipienteLabsys): Observable<EntityResponseType> {
        return this.http.post<IRecipienteLabsys>(this.resourceUrl, recipiente, { observe: 'response' });
    }

    update(recipiente: IRecipienteLabsys): Observable<EntityResponseType> {
        return this.http.put<IRecipienteLabsys>(this.resourceUrl, recipiente, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRecipienteLabsys>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRecipienteLabsys[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
