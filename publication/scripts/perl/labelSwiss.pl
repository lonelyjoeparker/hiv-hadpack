@data;
open(DATA, "ITMtotal_results.tdf");
while(<DATA>){
	push(@data, $_);
}
close(DATA);
print "read in ".scalar(@data)." codons of data.\n";

open(IN, $ARGV[0]) or die "can't open infile\n";
$infile = $ARGV[0];
open(ENTROPY, '>', "$infile.entropy.pdb") or die "can't open outfile\n";
open(SELECTION, '>', "$infile.selection.pdb") or die "can't open outfile\n";
open(DN, '>', "$infile.dN.pdb") or die "can't open outfile\n";
open(DS, '>', "$infile.dS.pdb") or die "can't open outfile\n";
open(PPOS, '>', "$infile.pPos.pdb") or die "can't open outfile\n";
open(PNEG, '>', "$infile.pNeg.pdb") or die "can't open outfile\n";
open(MAXEPIS, '>', "$infile.maxEpistatic.pdb") or die "can't open outfile\n";
open(AGGEPIS, '>', "$infile.aggEpistatic.pdb") or die "can't open outfile\n";

$residueCheck = 1;
while(<IN>){
	chomp($line = $_);
	if(substr($line,0,4) =~ /ATOM/){
		$entropy = $line;
		$selection = $line;
		$dN = $line;
		$dS = $line;
		$pPos = $line;
		$pNeg = $line;
		$maxEpis = $line;
		$aggEpis = $line;
		
		@fields = split(/ +/, $line);
		if($fields[2] eq 'N'){
			$residueCheck ++;
		}
		$residue = ($fields[4]);

		@evolData = split(/\t/,$data[$residue-1]);

		$normEntropy = (($evolData[11]/1.974)*100)-0.01;
		if($normEntropy <0.02){
			$normEntropy = 0.01;
		}
		$normEntropy = substr($normEntropy,0,5);
		substr($entropy, 61) = $normEntropy; 
#		print "$entropy\n";
		

		$pSelected = (((1-$evolData[5]))*100)-0.01;
		if($pSelected <0.02){
			$pSelected = 0.01;
		}
		$pSelected = substr($pSelected,0,5);
		substr($selection, 61) = $pSelected; 
#		print "$selection\n";


		$norm_dN = (($evolData[1]/36.1266)*100)-0.01;
		if($norm_dN <0.02){
			$norm_dN = 0.01;
		}
		$norm_dN = substr($norm_dN,0,5);
		substr($dN, 61) = $norm_dN; 
#		print "$dN\n";


		$norm_dS = (($evolData[1]/40.8197)*100)-0.01;
		if($norm_dS <0.02){
			$norm_dS = 0.01;
		}
		$norm_dS = substr($norm_dS,0,5);
		substr($dS, 61) = $norm_dS; 
#		print "$dS\n";

		if($evolData[2]>1){
			#dN > dS
			$pPosVal = (((1-$evolData[6]))*100);
			$pNegVal = "00.01";

		}else{
			if($evolData[2]<1){
				#dS > dN
				$pNegVal = (((1-$evolData[6]))*100);
				$pPosVal = "00.01";
	
			}else{
				#both are probably zero....
				$pPosVal = "00.01";
				$pNegVal = "00.01";
			}	
		}

		$pPosVal = substr($pPosVal,0,5);
		substr($pPos, 61) = $pPosVal; 
#		print "$pPos\n";

		$pNegVal = substr($pNegVal,0,5);
		substr($pNeg, 61) = $pNegVal; 
#		print "$pNeg\n";


		$maxEpisVal = ($evolData[9]*100)-0.01;
		if($maxEpisVal <0.02){
			$maxEpisVal = 0.01;
		}
		$maxEpisVal = substr($maxEpisVal,0,5);
		substr($maxEpis, 61) = $maxEpisVal; 
#		print "$maxEpis\n";


		$aggEpisVal = (($evolData[13]/7.4397)*100)-0.01;
		if($aggEpisVal <0.02){
			$aggEpisVal = 0.01;
		}
		$aggEpisVal = substr($aggEpisVal,0,5);
		substr($aggEpis, 61) = $aggEpisVal; 
		print "$aggEpis\n";


		print ENTROPY "$entropy\n";
		print SELECTION "$selection\n";
		print DN "$dN\n";
		print DS "$dS\n";
		print PPOS "$pPos\n";
		print PNEG "$pNeg\n";
		print MAXEPIS "$maxEpis\n";
		print AGGEPIS "$aggEpis\n";
	}else{
		print ENTROPY "$line\n";
		print SELECTION "$line\n";
		print DN "$line\n";
		print DS "$line\n";
		print PPOS "$line\n";
		print PNEG "$line\n";
		print MAXEPIS "$line\n";
		print AGGEPIS "$line\n";
	}
}
close(IN);
close(ENTROPY);
close(SELECTION);
close(DN);
close(DS);
close(PPOS);
close(PNEG);
close(MAXEPIS);
close(AGGEPIS);
