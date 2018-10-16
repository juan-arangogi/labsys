/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LabsysTestModule } from '../../../test.module';
import { MedicoLabsysDetailComponent } from 'app/entities/medico-labsys/medico-labsys-detail.component';
import { MedicoLabsys } from 'app/shared/model/medico-labsys.model';

describe('Component Tests', () => {
    describe('MedicoLabsys Management Detail Component', () => {
        let comp: MedicoLabsysDetailComponent;
        let fixture: ComponentFixture<MedicoLabsysDetailComponent>;
        const route = ({ data: of({ medico: new MedicoLabsys(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [LabsysTestModule],
                declarations: [MedicoLabsysDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MedicoLabsysDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MedicoLabsysDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.medico).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
