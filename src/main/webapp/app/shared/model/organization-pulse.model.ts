import { IOrgAdminPulse } from 'app/shared/model//org-admin-pulse.model';

export interface IOrganizationPulse {
    id?: number;
    organizationName?: string;
    eventsId?: number;
    admins?: IOrgAdminPulse[];
}

export class OrganizationPulse implements IOrganizationPulse {
    constructor(public id?: number, public organizationName?: string, public eventsId?: number, public admins?: IOrgAdminPulse[]) {}
}
