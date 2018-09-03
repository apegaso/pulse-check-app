/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { OrganizationPulseDetailComponent } from 'app/entities/organization-pulse/organization-pulse-detail.component';
import { OrganizationPulse } from 'app/shared/model/organization-pulse.model';

describe('Component Tests', () => {
    describe('OrganizationPulse Management Detail Component', () => {
        let comp: OrganizationPulseDetailComponent;
        let fixture: ComponentFixture<OrganizationPulseDetailComponent>;
        const route = ({ data: of({ organization: new OrganizationPulse(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [OrganizationPulseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OrganizationPulseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OrganizationPulseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.organization).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
