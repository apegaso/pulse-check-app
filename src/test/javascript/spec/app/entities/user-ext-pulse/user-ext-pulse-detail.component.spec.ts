/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { UserExtPulseDetailComponent } from 'app/entities/user-ext-pulse/user-ext-pulse-detail.component';
import { UserExtPulse } from 'app/shared/model/user-ext-pulse.model';

describe('Component Tests', () => {
    describe('UserExtPulse Management Detail Component', () => {
        let comp: UserExtPulseDetailComponent;
        let fixture: ComponentFixture<UserExtPulseDetailComponent>;
        const route = ({ data: of({ userExt: new UserExtPulse(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [UserExtPulseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(UserExtPulseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(UserExtPulseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.userExt).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
