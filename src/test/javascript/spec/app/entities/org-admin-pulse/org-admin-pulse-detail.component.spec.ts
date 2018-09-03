/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { OrgAdminPulseDetailComponent } from 'app/entities/org-admin-pulse/org-admin-pulse-detail.component';
import { OrgAdminPulse } from 'app/shared/model/org-admin-pulse.model';

describe('Component Tests', () => {
    describe('OrgAdminPulse Management Detail Component', () => {
        let comp: OrgAdminPulseDetailComponent;
        let fixture: ComponentFixture<OrgAdminPulseDetailComponent>;
        const route = ({ data: of({ orgAdmin: new OrgAdminPulse(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [OrgAdminPulseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrgAdminPulseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrgAdminPulseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.orgAdmin).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
