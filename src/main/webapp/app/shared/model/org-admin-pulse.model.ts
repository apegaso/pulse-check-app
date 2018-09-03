import { IOrganizationPulse } from 'app/shared/model//organization-pulse.model';

export interface IOrgAdminPulse {
    id?: number;
    organizations?: IOrganizationPulse[];
}

export class OrgAdminPulse implements IOrgAdminPulse {
    constructor(public id?: number, public organizations?: IOrganizationPulse[]) {}
}
