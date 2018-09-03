import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClientLeadPulse } from 'app/shared/model/client-lead-pulse.model';

type EntityResponseType = HttpResponse<IClientLeadPulse>;
type EntityArrayResponseType = HttpResponse<IClientLeadPulse[]>;

@Injectable({ providedIn: 'root' })
export class ClientLeadPulseService {
    private resourceUrl = SERVER_API_URL + 'api/client-leads';

    constructor(private http: HttpClient) {}

    create(clientLead: IClientLeadPulse): Observable<EntityResponseType> {
        return this.http.post<IClientLeadPulse>(this.resourceUrl, clientLead, { observe: 'response' });
    }

    update(clientLead: IClientLeadPulse): Observable<EntityResponseType> {
        return this.http.put<IClientLeadPulse>(this.resourceUrl, clientLead, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IClientLeadPulse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IClientLeadPulse[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
