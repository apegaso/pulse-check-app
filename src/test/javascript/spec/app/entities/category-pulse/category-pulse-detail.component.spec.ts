/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PulseCheckAppTestModule } from '../../../test.module';
import { CategoryPulseDetailComponent } from 'app/entities/category-pulse/category-pulse-detail.component';
import { CategoryPulse } from 'app/shared/model/category-pulse.model';

describe('Component Tests', () => {
    describe('CategoryPulse Management Detail Component', () => {
        let comp: CategoryPulseDetailComponent;
        let fixture: ComponentFixture<CategoryPulseDetailComponent>;
        const route = ({ data: of({ category: new CategoryPulse(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PulseCheckAppTestModule],
                declarations: [CategoryPulseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CategoryPulseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CategoryPulseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.category).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
