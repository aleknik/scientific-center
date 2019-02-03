import { Author } from './author.model';

export class Paper {
    title: string;
    paperAbstract: string;
    keywords: string[];
    scienceField: string;
    coauthors: Author[];
}
