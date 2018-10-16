/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { RecipienteLabsysDetailComponent } from 'app/entities/recipiente-labsys/recipiente-labsys-detail.component';
import { RecipienteLabsys } from 'app/shared/model/recipiente-labsys.model';

describe('Component Tests', () => {
    describe('RecipienteLabsys Management Detail Component', () => {
        let comp: RecipienteLabsysDetailComponent;
        let fixture: ComponentFixture<RecipienteLabsysDetailComponent>;
        const route = ({ data: of({ recipiente: new RecipienteLabsys(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [RecipienteLabsysDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RecipienteLabsysDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecipienteLabsysDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.recipiente).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
